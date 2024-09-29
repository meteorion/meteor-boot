package pers.meteor.pay.domain.channel.module;

import lombok.Data;
import pers.meteor.common.core.enums.SwitchStatusEnum;
import pers.meteor.common.core.exception.ServiceException;
import pers.meteor.pay.domain.channel.module.enums.PayChannelEnum;
import pers.meteor.pay.domain.channel.module.valueobject.Quota;
import pers.meteor.pay.domain.channel.module.valueobject.Rate;

import java.util.*;

/**
 * 支付运用
 *
 * @author meteor
 */
@Data
public class PayApp {
    /**
     * 通道id
     */
    private Long appId;
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
     * 运用限额
     */
    private Quota quota;
    /**
     * 通道配置
     */
    private EnumMap<PayChannelEnum, PayChannel> payChannels;

    public PayApp() {
        this.status = SwitchStatusEnum.OPEN;
        this.payChannels = new EnumMap<>(PayChannelEnum.class);
    }

    /**
     * 更新通道配置
     *
     * @param newPayChannel /
     */
    public void updateChannel(PayApp newPayChannel) {
        // 更细基本信息
        if (newPayChannel.getStatus() != null) {
            this.status = newPayChannel.getStatus();
        }
        // 更新通道配置
        if (newPayChannel.getPayChannels() != null) {
            this.updateConfig(newPayChannel.getPayChannels().values());
        }
        if (newPayChannel.getQuota() != null) {
            this.quota = newPayChannel.getQuota();
        }
    }

    /**
     * 获取通道配置
     *
     * @param channelTyle /
     * @return /
     */
    public PayChannel getChannelConfig(PayChannelEnum channelTyle) {
        if (payChannels == null) {
            return null;
        }
        return payChannels.get(channelTyle);
    }

    /**
     * 添加通道配置
     *
     * @param payChannel /
     */
    public void addPayChannel(PayChannel payChannel) {
        if (payChannels == null) {
            payChannels = new EnumMap<>(PayChannelEnum.class);
        }
        payChannels.put(payChannel.getChannelType(), payChannel);
    }

    /**
     * 修改通道配置
     *
     * @param channelConfigs /
     */
    public void updateConfig(Collection<PayChannel> channelConfigs) {
        channelConfigs.forEach(this::updateConfig);
    }

    /**
     * 更新配置
     *
     * @param newChannelConfig /
     */
    public void updateConfig(PayChannel newChannelConfig) {
        PayChannelEnum channelType = newChannelConfig.getChannelType();
        PayChannel channelConfig = getChannelConfig(channelType);
        if (!Objects.equals(channelConfig.getChannelId(), newChannelConfig.getChannelId())) {
            throw new ServiceException("支付类型配置已存在");
        }
        this.payChannels.put(channelType, newChannelConfig);
    }

    /**
     * 添加通道配置
     *
     * @param channelConfig /
     */
    public void addConfig(PayChannel channelConfig) {
        PayChannelEnum channelType = channelConfig.getChannelType();
        if (this.payChannels.containsKey(channelType)) {
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
    public Rate getChannelRate(PayChannelEnum payType) {
        if (payChannels == null || payChannels.get(payType) == null) {
            return null;
        }
        return payChannels.get(payType).getChannelRate();
    }

    /**
     * 获取通道费率
     *
     * @return /
     */
    public EnumMap<PayChannelEnum, Rate> getChannelRates() {
        EnumMap<PayChannelEnum, Rate> channelRates = new EnumMap<>(PayChannelEnum.class);
        if (this.payChannels == null) {
            return channelRates;
        }
        for (Map.Entry<PayChannelEnum, PayChannel> entry : this.payChannels.entrySet()) {
            PayChannel channelConfig = entry.getValue();
            Optional<Rate> channelRateOptional = Optional.ofNullable(channelConfig.getChannelRate());
            channelRateOptional.ifPresent(rate -> channelRates.put(entry.getKey(), rate));
        }
        return channelRates;
    }

    /**
     * 校验费率配置
     *
     * @param defaultRates /
     */
    public void checkRates(List<Rate> defaultRates) {
        for (Rate defaultRate : defaultRates) {
            PayChannelEnum payChannel = defaultRate.getChannelType();
            Rate channelRate = getChannelRate(payChannel);
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
    public void updateRate(Rate channelRate) {
        PayChannelEnum payType = channelRate.getChannelType();
        PayChannel channelConfig = this.payChannels.get(payType);
        if (channelConfig == null) {
            throw new ServiceException("获取通道配置失败");
        }
        channelConfig.setChannelRate(channelRate);
    }
}
