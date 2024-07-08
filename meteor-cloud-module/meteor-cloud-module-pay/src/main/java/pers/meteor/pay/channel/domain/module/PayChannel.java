package pers.meteor.pay.channel.domain.module;

import lombok.Data;
import pers.meteor.common.core.enums.SwitchStatusEnum;
import pers.meteor.pay.channel.domain.module.enums.RateTypeEnum;
import pers.meteor.pay.channel.domain.module.enums.ChannelTypeEnum;
import pers.meteor.pay.channel.domain.module.valueobject.ChannelConfig;
import pers.meteor.pay.channel.domain.module.valueobject.ChannelRate;

import java.util.EnumMap;

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
     * 通道类型
     */
    private ChannelTypeEnum channelType;
    /**
     * 是否可用
     */
    private SwitchStatusEnum enabled;
    /**
     * 通道配置
     */
    private ChannelConfig channelConfig;
    /**
     * 通道费率
     */
    private EnumMap<RateTypeEnum, ChannelRate> channelRates;

    /**
     * 更新通道配置
     *
     * @param newPayChannel /
     */
    public void updateChannel(PayChannel newPayChannel) {
        // 更细基本信息
        // 更新通道配置
        this.updateConfig(newPayChannel.getChannelConfig());
        // 更细费率
        this.updateRate(newPayChannel.getChannelRates());
    }

    /**
     * 修改通道配置
     *
     * @param channelConfig /
     */
    public void updateConfig(ChannelConfig channelConfig) {

    }

    /**
     * 修改费率
     *
     * @param channelRates /
     */
    public void updateRate(EnumMap<RateTypeEnum, ChannelRate> channelRates) {

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
     * 校验费率配置
     *
     * @param defaultRates /
     */
    public void checkRates(EnumMap<RateTypeEnum, ChannelConfig> defaultRates) {

    }
}
