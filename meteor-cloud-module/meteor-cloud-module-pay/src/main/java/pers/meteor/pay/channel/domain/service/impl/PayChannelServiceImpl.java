package pers.meteor.pay.channel.domain.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pers.meteor.common.core.exception.ServiceException;
import pers.meteor.pay.channel.domain.acl.RemotePayConfigAdapter;
import pers.meteor.pay.channel.domain.module.PayChannel;
import pers.meteor.pay.channel.domain.module.valueobject.ChannelConfig;
import pers.meteor.pay.channel.domain.module.valueobject.ChannelRate;
import pers.meteor.pay.channel.domain.module.enums.RateTypeEnum;
import pers.meteor.pay.channel.domain.module.valueobject.SystemChannelConfig;
import pers.meteor.pay.channel.domain.repository.PayChannelRepository;
import pers.meteor.pay.channel.domain.service.PayChannelService;

import javax.annotation.Resource;
import java.util.EnumMap;
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
        PayChannel payChannel = checkChannelExists(newPayChannel.getChannelId());
        payChannel.updateChannel(newPayChannel);
        // 检查通道信息是否存在
        checkChannelUnique(payChannel);
        // 检查通道费率
        checkChannelRate(payChannel);
        // 更新数据
        payChannelRepository.save(payChannel);
    }

    @Override
    public void updateChannelConfig(Long channelId, ChannelConfig channelConfig) {
        // 检查通道ID是否存在
        PayChannel payChannel = checkChannelExists(channelId);
        payChannel.updateConfig(channelConfig);
        // 更新数据
        payChannelRepository.save(payChannel);
    }

    @Override
    public void updateChannelRate(Long channelId, EnumMap<RateTypeEnum, ChannelRate> channelRates) {
        // 检查通道ID是否存在
        PayChannel payChannel = checkChannelExists(channelId);
        payChannel.updateRate(channelRates);
        // 检查通道费率
        checkChannelRate(payChannel);
        // 更新数据
        payChannelRepository.save(payChannel);
    }

    @Override
    public void enabled(Long channelId, boolean enabled) {
        // 检查通道ID是否存在
        PayChannel payChannel = checkChannelExists(channelId);
        if (enabled) {
            payChannel.open();
        } else {
            payChannel.close();
        }
        // 更新数据
        payChannelRepository.save(payChannel);
    }

    /**
     * 检查通道唯一性
     *
     * @param channelId 通道id
     */
    private PayChannel checkChannelExists(Long channelId) {
        PayChannel payChannel = payChannelRepository.selectById(channelId);
        if (payChannel == null) {
            throw new ServiceException("通道配置不存在");
        }
        return payChannel;
    }

    /**
     * 检查通道唯一性
     *
     * @param payChannel 通道配置
     */
    private void checkChannelUnique(PayChannel payChannel) {
        PayChannel payChannelDb = payChannelRepository.selectByName(payChannel.getName());
        if (payChannelDb != null && Objects.equals(payChannelDb.getChannelId(), payChannel.getChannelId())) {
            throw new ServiceException("通道名称已配置");
        }
        SystemChannelConfig systemChannelConfig = configAdapter.getSystemChannelConfig();
        if (systemChannelConfig.isUniqueCode()) {
            List<PayChannel> payChannels = payChannelRepository.selectByCode(payChannel.getCode());
            long count = payChannels.stream().filter(item -> !Objects.equals(item.getChannelId(), payChannel.getChannelId())).count();
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
        // 1. 获取系统费率配置
        EnumMap<RateTypeEnum, ChannelConfig> defaultRates = configAdapter.getDefaultRates();
        // 2. 校验费率
        payChannel.checkRates(defaultRates);
    }

}
