package pers.meteor.pay.domain.channel.module;

import lombok.Data;
import pers.meteor.common.core.enums.SwitchStatusEnum;
import pers.meteor.common.core.exception.ServiceException;
import pers.meteor.pay.domain.channel.module.enums.PayChannelEnum;
import pers.meteor.pay.domain.channel.module.valueobject.ChannelQuota;
import pers.meteor.pay.domain.channel.module.valueobject.ChannelRate;

import java.util.*;

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
    private Long payChannelId;
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
    private SwitchStatusEnum status;
    /**
     * 通道费率
     */
    private ChannelQuota channelQuota;
    /**
     * 通道配置
     */
    private EnumMap<PayChannelEnum, ChannelConfig> channelConfigs;
    /**
     * 支付订单回调地址
     */
    private String orderNotifyUrl;
    /**
     * 退款订单回调地址
     */
    private String refundNotifyUrl;

    public PayChannel() {
        this.status = SwitchStatusEnum.OPEN;
        this.channelConfigs = new EnumMap<>(PayChannelEnum.class);
    }

    /**
     * 更新通道配置
     *
     * @param newPayChannel /
     */
    public void updateChannel(PayChannel newPayChannel) {
        // 更细基本信息
        if (newPayChannel.getStatus() != null) {
            this.status = newPayChannel.getStatus();
        }
        // 更新通道配置
        if (newPayChannel.getChannelConfigs() != null) {
            this.updateConfig(newPayChannel.getChannelConfigs().values());
        }
        if (newPayChannel.getChannelQuota() != null) {
            this.channelQuota = newPayChannel.getChannelQuota();
        }
    }

    /**
     * 获取通道配置
     *
     * @param channelTyle /
     * @return /
     */
    public ChannelConfig getChannelConfig(PayChannelEnum channelTyle) {
        if (channelConfigs == null) {
            return null;
        }
        return channelConfigs.get(channelTyle);
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
        PayChannelEnum channelType = newChannelConfig.getChannelType();
        ChannelConfig channelConfig = getChannelConfig(channelType);
        if (!Objects.equals(channelConfig.getChannelConfigId(), newChannelConfig.getChannelConfigId())) {
            throw new ServiceException("支付类型配置已存在");
        }
        this.channelConfigs.put(channelType, newChannelConfig);
    }

    /**
     * 添加通道配置
     *
     * @param channelConfig /
     */
    public void addConfig(ChannelConfig channelConfig) {
        PayChannelEnum channelType = channelConfig.getChannelType();
        if (this.channelConfigs.containsKey(channelType)) {
            throw new ServiceException("支付类型配置已存在");
        }
    }

    /**
     * 打开通道
     */
    public void open() {
        this.status = SwitchStatusEnum.OPEN;
    }

    /**
     * 关闭通道
     */
    public void close() {
        this.status = SwitchStatusEnum.CLOSE;
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
            Optional<ChannelRate> channelRateOptional = Optional.ofNullable(channelConfig.getChannelRate());
            channelRateOptional.ifPresent(rate -> channelRates.put(entry.getKey(), rate));
        }
        return channelRates;
    }

    /**
     * 校验费率配置
     *
     * @param defaultRates /
     */
    public void checkRates(List<ChannelRate> defaultRates) {
        for (ChannelRate defaultRate : defaultRates) {
            PayChannelEnum payChannel = defaultRate.getChannelType();
            ChannelRate channelRate = getChannelRate(payChannel);
            if (channelRate == null) {
                continue;
            }
            defaultRate.checkRateConfig(channelRate);
        }
    }

    /**
     * 修改费率
     *
     * @param channelRate /
     */
    public void updateRate(ChannelRate channelRate) {
        PayChannelEnum payType = channelRate.getChannelType();
        ChannelConfig channelConfig = this.channelConfigs.get(payType);
        if (channelConfig == null) {
            throw new ServiceException("获取通道配置失败");
        }
        channelConfig.setChannelRate(channelRate);
    }
}
