package pers.meteor.pay.domain.channel.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import pers.meteor.common.core.exception.ServiceException;
import pers.meteor.pay.domain.channel.acl.RemotePayConfigAdapter;
import pers.meteor.pay.domain.channel.module.ChannelConfig;
import pers.meteor.pay.domain.channel.module.PayChannel;
import pers.meteor.pay.domain.channel.module.PayClientConfig;
import pers.meteor.pay.domain.channel.module.valueobject.ChannelRate;
import pers.meteor.pay.domain.channel.module.valueobject.SystemChannelConfig;
import pers.meteor.pay.domain.channel.repository.PayChannelRepository;
import pers.meteor.pay.domain.channel.service.PayChannelService;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * 支付通道服务层
 *
 * @author meteor
 */
@Service
@Slf4j
@Setter
@RequiredArgsConstructor
public class PayChannelServiceImpl implements PayChannelService {
    private final PayChannelRepository payChannelRepository;
    @Resource
    private RemotePayConfigAdapter configAdapter;

    @Override
    public Long addPayChannel(PayChannel payChannel) {
        // 检查通道信息是否存在
        checkChannelUnique(payChannel);
        // 检查通道费率
        checkChannelRate(payChannel);
        // 保存数据
        return payChannelRepository.save(payChannel);
    }

    @Override
    public void updatePayChannel(PayChannel newPayChannel) {
        // 检查通道ID是否存在
        PayChannel payChannel = checkChannelExists(newPayChannel.getPayChannelId());
        payChannel.updateChannel(newPayChannel);
        // 检查通道信息是否存在
        checkChannelUnique(payChannel);
        // 检查通道费率
        checkChannelRate(payChannel);
        // 更新数据
        payChannelRepository.save(payChannel);
    }

    @Override
    public Long addChannelConfig(ChannelConfig channelConfig) {
        // 检查通道ID是否存在
        PayChannel payChannel = checkChannelExists(channelConfig.getPayChannelId());
        payChannel.addConfig(channelConfig);
        // 检查费率
        checkChannelRate(payChannel);
        // 更新数据
        return payChannelRepository.saveChannelConfig(channelConfig);
    }

    @Override
    public void updateChannelConfig(ChannelConfig channelConfig) {
        // 检查通道ID是否存在
        PayChannel payChannel = checkChannelExists(channelConfig.getPayChannelId());
        payChannel.updateConfig(channelConfig);
        // 检查费率
        checkChannelRate(payChannel);
        // 更新数据
        payChannelRepository.saveChannelConfig(channelConfig);
    }

    @Override
    public void updateClientConfig(PayClientConfig clientConfig) {
        // 检查通道ID是否存在
        ChannelConfig channelConfig = checkChannelConfigExists(clientConfig.getChannelConfigId());
        channelConfig.setPayClientConfig(clientConfig);
        // 更新数据
        payChannelRepository.saveChannelConfig(channelConfig);
    }

    @Override
    public void updateChannelRate(Long payChannelConfigId, ChannelRate channelRate) {
        // 检查通道ID是否存在
        ChannelConfig channelConfig = checkChannelConfigExists(payChannelConfigId);
        channelConfig.setChannelRate(channelRate);
        // 检查通道费率
        checkChannelRate(channelConfig);
        // 更新数据
        payChannelRepository.saveChannelRate(payChannelConfigId, channelConfig.getChannelRate());
    }

    @Override
    public void enabled(Long payChannelId, boolean enabled) {
        // 检查通道ID是否存在
        PayChannel payChannel = checkChannelExists(payChannelId);
        if (enabled) {
            payChannel.open();
        } else {
            payChannel.close();
        }
        // 更新数据
        payChannelRepository.updateChannelStatus(payChannelId, payChannel.getStatus());
    }

    @Override
    public void delete(Long payChannelId) {
        checkChannelExists(payChannelId);
        payChannelRepository.delete(payChannelId);
    }

    @Override
    public void deleteChannelConfig(Long payChannelConfigId) {
        checkChannelConfigExists(payChannelConfigId);
        payChannelRepository.deleteChannelConfig(payChannelConfigId);
    }

    /**
     * 检查通道唯一性
     *
     * @param channelId 通道id
     */
    private PayChannel checkChannelExists(Long channelId) {
        PayChannel payChannel = payChannelRepository.selectById(channelId);
        if (payChannel == null) {
            throw new ServiceException("通道不存在");
        }
        return payChannel;
    }

    /**
     * 校验通道配置
     *
     * @param channelConfigId /
     * @return /
     */
    private ChannelConfig checkChannelConfigExists(Long channelConfigId) {
        ChannelConfig channelConfig = payChannelRepository.selectPayChannelConfig(channelConfigId);
        if (channelConfig == null) {
            throw new ServiceException("通道配置不存在");
        }
        return channelConfig;
    }

    /**
     * 检查通道唯一性
     *
     * @param payChannel 通道配置
     */
    private void checkChannelUnique(PayChannel payChannel) {
        PayChannel payChannelDb = payChannelRepository.selectByName(payChannel.getName());
        if (payChannelDb != null && !Objects.equals(payChannelDb.getPayChannelId(), payChannel.getPayChannelId())) {
            throw new ServiceException("通道名称已配置");
        }
        SystemChannelConfig systemChannelConfig = configAdapter.getSystemChannelConfig();
        if (systemChannelConfig.isUniqueCode()) {
            List<PayChannel> payChannels = payChannelRepository.selectByCode(payChannel.getCode());
            long count = payChannels.stream().filter(item -> !Objects.equals(item.getPayChannelId(), payChannel.getPayChannelId())).count();
            if (count > 0) {
                throw new ServiceException("通道代号已配置");
            }
        }
    }

    /**
     * 检查通道费率
     *
     * @param payChannel 通道配置
     */
    private void checkChannelRate(PayChannel payChannel) {
        if (CollectionUtils.isEmpty(payChannel.getChannelRates().values())) {
            return;
        }
        // 1. 获取系统费率配置
        List<ChannelRate> defaultRates = configAdapter.getDefaultRates();
        // 2. 校验费率
        payChannel.checkRates(defaultRates);
    }

    private void checkChannelRate(ChannelConfig channelConfig) {
        ChannelRate channelRate = channelConfig.getChannelRate();
        if (channelRate == null) {
            throw new ServiceException("通道费率不能为空");
        }
        // 1. 获取系统费率配置
        List<ChannelRate> defaultRates = configAdapter.getDefaultRates();
        for (ChannelRate defaultRate : defaultRates) {
            if (channelRate.getChannelType().equals(defaultRate.getChannelType())) {
                defaultRate.checkRateConfig(channelRate);
            }
        }
    }

}
