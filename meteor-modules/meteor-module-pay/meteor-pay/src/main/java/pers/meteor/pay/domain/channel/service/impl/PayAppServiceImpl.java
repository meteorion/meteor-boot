package pers.meteor.pay.domain.channel.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import pers.meteor.common.exception.ServiceException;
import pers.meteor.pay.domain.channel.acl.RemotePayConfigAdapter;
import pers.meteor.pay.domain.channel.module.PayApp;
import pers.meteor.pay.domain.channel.module.PayChannel;
import pers.meteor.pay.domain.channel.module.PayClient;
import pers.meteor.pay.domain.channel.module.valueobject.Rate;
import pers.meteor.pay.domain.channel.module.valueobject.SystemChannelConfig;
import pers.meteor.pay.domain.channel.repository.PayAppRepository;
import pers.meteor.pay.domain.channel.service.PayAppService;

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
public class PayAppServiceImpl implements PayAppService {
    private final PayAppRepository payAppRepository;
    @Resource
    private RemotePayConfigAdapter configAdapter;

    @Override
    public Long addPayApp(PayApp payApp) {
        // 检查通道信息是否存在
        checkPayAppUnique(payApp);
        // 检查通道费率
        checkChannelRate(payApp);
        // 保存数据
        return payAppRepository.save(payApp);
    }

    @Override
    public void updatePayApp(PayApp newPayApp) {
        // 检查通道ID是否存在
        PayApp payApp = checkChannelExists(newPayApp.getAppId());
        payApp.updateChannel(newPayApp);
        // 检查通道信息是否存在
        checkPayAppUnique(payApp);
        // 检查通道费率
        checkChannelRate(payApp);
        // 更新数据
        payAppRepository.save(payApp);
    }

    @Override
    public Long addPayChannel(PayChannel payChannel) {
        // 检查通道ID是否存在
        PayApp payApp = checkChannelExists(payChannel.getAppId());
        payApp.addConfig(payChannel);
        // 检查费率
        checkChannelRate(payApp);
        // 更新数据
        return payAppRepository.savePayChannel(payChannel);
    }

    @Override
    public void updatePayChannel(PayChannel payChannel) {
        // 检查通道ID是否存在
        PayApp payApp = checkChannelExists(payChannel.getAppId());
        payApp.updateConfig(payChannel);
        // 检查费率
        checkChannelRate(payApp);
        // 更新数据
        payAppRepository.savePayChannel(payChannel);
    }

    @Override
    public void updatePayClient(PayClient payClient) {
        // 检查通道ID是否存在
        PayChannel payChannel = checkPayChannelExists(payClient.getClientId());
        payChannel.setPayClient(payClient);
        // 更新数据
        payAppRepository.savePayChannel(payChannel);
    }

    @Override
    public void updateChannelRate(Long channelId, Rate channelRate) {
        // 检查通道ID是否存在
        PayChannel payChannel = checkPayChannelExists(channelId);
        payChannel.setChannelRate(channelRate);
        // 检查通道费率
        checkChannelRate(payChannel);
        // 更新数据
        payAppRepository.saveRate(channelId, payChannel.getChannelRate());
    }

    @Override
    public void enabled(Long appId, boolean enabled) {
        // 检查通道ID是否存在
        PayApp payApp = checkChannelExists(appId);
        if (enabled) {
            payApp.open();
        } else {
            payApp.close();
        }
        // 更新数据
        payAppRepository.updatePayAppStatus(appId, payApp.getStatus());
    }

    @Override
    public void deleteApp(Long appId) {
        checkChannelExists(appId);
        payAppRepository.deleteApp(appId);
    }

    @Override
    public void deletePayChannel(Long channelId) {
        checkPayChannelExists(channelId);
        payAppRepository.deletePayChannel(channelId);
    }

    /**
     * 检查通道唯一性
     *
     * @param channelId 通道id
     */
    private PayApp checkChannelExists(Long channelId) {
        PayApp payChannel = payAppRepository.selectById(channelId);
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
    private PayChannel checkPayChannelExists(Long channelConfigId) {
        PayChannel payChannel = payAppRepository.selectPayChannel(channelConfigId);
        if (payChannel == null) {
            throw new ServiceException("通道配置不存在");
        }
        return payChannel;
    }

    /**
     * 检查通道唯一性
     *
     * @param newPayApp 通道配置
     */
    private void checkPayAppUnique(PayApp newPayApp) {
        PayApp payApp = payAppRepository.selectByName(newPayApp.getName());
        if (payApp != null && !Objects.equals(payApp.getAppId(), newPayApp.getAppId())) {
            throw new ServiceException("通道名称已配置");
        }
        SystemChannelConfig systemChannelConfig = configAdapter.getSystemChannelConfig();
        if (systemChannelConfig.isUniqueCode()) {
            List<PayApp> payApps = payAppRepository.selectByCode(newPayApp.getCode());
            long count = payApps.stream().filter(item -> !Objects.equals(item.getAppId(), newPayApp.getAppId())).count();
            if (count > 0) {
                throw new ServiceException("通道代号已配置");
            }
        }
    }

    /**
     * 检查通道费率
     *
     * @param payApp 通道配置
     */
    private void checkChannelRate(PayApp payApp) {
        if (CollectionUtils.isEmpty(payApp.getChannelRates().values())) {
            return;
        }
        // 1. 获取系统费率配置
        List<Rate> defaultRates = configAdapter.getDefaultRates();
        // 2. 校验费率
        payApp.checkRates(defaultRates);
    }

    private void checkChannelRate(PayChannel payChannel) {
        Rate channelRate = payChannel.getChannelRate();
        if (channelRate == null) {
            throw new ServiceException("通道费率不能为空");
        }
        // 1. 获取系统费率配置
        List<Rate> defaultRates = configAdapter.getDefaultRates();
        for (Rate defaultRate : defaultRates) {
            if (channelRate.getChannelType().equals(defaultRate.getChannelType())) {
                defaultRate.checkRateConfig(channelRate);
            }
        }
    }

}
