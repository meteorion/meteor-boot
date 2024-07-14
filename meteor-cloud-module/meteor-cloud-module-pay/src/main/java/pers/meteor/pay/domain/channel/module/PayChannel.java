package pers.meteor.pay.domain.channel.module;

import lombok.Data;
import pers.meteor.common.core.enums.SwitchStatusEnum;
import pers.meteor.common.core.exception.ServiceException;
import pers.meteor.pay.domain.channel.module.enums.PayChannelEnum;
import pers.meteor.pay.domain.channel.module.valueobject.ChannelQuota;
import pers.meteor.pay.domain.channel.module.valueobject.ChannelRate;

import java.util.Collection;
import java.util.EnumMap;
import java.util.Map;

/**
 * 支付通道
 *
 * @author meteor
 */
@Data
public class PayChannel {
    /**
     * 通道id
     */
    private Long channelId;
    /**
     * 通道代号
     */
    private String code;
    /**
     * 通道名称
     */
    private String name;
    /**
     * 是否可用
     */
    private SwitchStatusEnum enabled;
    /**
     * 通道配置
     */
    private EnumMap<PayChannelEnum, ChannelConfig> channelConfigs;
    /**
     * 通道费率
     */
    private ChannelQuota channelQuota;

    public PayChannel() {
        this.enabled = SwitchStatusEnum.OPEN;
        this.channelConfigs = new EnumMap<>(PayChannelEnum.class);
    }

    /**
     * 更新通道配置
     *
     * @param newPayChannel /
     */
    public void updateChannel(PayChannel newPayChannel) {
        // 更细基本信息
        if (newPayChannel.getEnabled() != null) {
            this.enabled = newPayChannel.getEnabled();
        }
        // 更新通道配置
        this.updateConfig(newPayChannel.getChannelConfigs().values());
    }

    /**
     * 添加通道配置
     *
     * @param channelConfig /
     */
    public void addChanneConfig(ChannelConfig channelConfig) {
        if (channelConfigs == null) {
            channelConfigs = new EnumMap<>(PayChannelEnum.class);
        }
        channelConfigs.put(channelConfig.getChannelType(), channelConfig);
    }

    /**
     * 修改通道配置
     *
     * @param channelConfigs /
     */
    public void updateConfig(Collection<ChannelConfig> channelConfigs) {
        channelConfigs.forEach(this::updateConfig);
    }

    /**
     * 更新配置
     *
     * @param newChannelConfig /
     */
    public void updateConfig(ChannelConfig newChannelConfig) {
        PayChannelEnum payType = newChannelConfig.getChannelType();
        this.channelConfigs.put(payType, newChannelConfig);
    }

    /**
     * 打开通道
     */
    public void open() {
        this.enabled = SwitchStatusEnum.OPEN;
    }

    /**
     * 关闭通道
     */
    public void close() {
        this.enabled = SwitchStatusEnum.CLOSE;
    }

    /**
     * 获取通道支付费率
     *
     * @param payType 支付类型
     * @return 通道费率
     */
    public ChannelRate getChannelRate(PayChannelEnum payType) {
        if (channelConfigs == null || channelConfigs.get(payType) == null) {
            return null;
        }
        return channelConfigs.get(payType).getChannelRate();
    }

    /**
     * 获取通道费率
     *
     * @return /
     */
    public EnumMap<PayChannelEnum, ChannelRate> getChannelRates() {
        EnumMap<PayChannelEnum, ChannelRate> channelRates = new EnumMap<>(PayChannelEnum.class);
        if (this.channelConfigs == null) {
            return channelRates;
        }
        for (Map.Entry<PayChannelEnum, ChannelConfig> entry : this.channelConfigs.entrySet()) {
            ChannelConfig channelConfig = entry.getValue();
            if (channelConfig.getChannelRate() != null) {
                channelRates.put(entry.getKey(), channelConfig.getChannelRate());
            }
        }
        return channelRates;
    }

    /**
     * 校验费率配置
     *
     * @param defaultRates /
     */
    public void checkRates(EnumMap<PayChannelEnum, ChannelRate> defaultRates) {
        for (Map.Entry<PayChannelEnum, ChannelRate> entry : defaultRates.entrySet()) {
            PayChannelEnum payType = entry.getKey();
            ChannelRate defaultRate = entry.getValue();
            ChannelRate channelRate = getChannelRate(payType);
            if (channelRate == null) {
                continue;
            }
            defaultRate.checkRateConfig(channelRate);
        }
    }

    /**
     * 修改费率
     *
     * @param channelRates /
     */
    public void updateRate(Collection<ChannelRate> channelRates) {
        channelRates.forEach(this::updateRate);
    }

    /**
     * 修改费率
     *
     * @param channelRate /
     */
    public void updateRate(ChannelRate channelRate) {
        PayChannelEnum payType = channelRate.getPayChannel();
        ChannelConfig channelConfig = this.channelConfigs.get(payType);
        if (channelConfig == null) {
            throw new ServiceException("获取通道配置失败");
        }
        channelConfig.setChannelRate(channelRate);
    }
}
