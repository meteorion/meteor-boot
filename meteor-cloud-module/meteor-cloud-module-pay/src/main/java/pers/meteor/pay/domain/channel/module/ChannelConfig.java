package pers.meteor.pay.domain.channel.module;

import lombok.Data;
import pers.meteor.common.core.enums.SwitchStatusEnum;
import pers.meteor.pay.domain.channel.module.enums.PayChannelEnum;
import pers.meteor.pay.domain.channel.module.valueobject.ChannelQuota;
import pers.meteor.pay.domain.channel.module.valueobject.ChannelRate;
import pers.meteor.pay.domain.channel.module.valueobject.TimeRange;

/**
 * 通道配置
 *
 * @author meteor
 */
@Data
public class ChannelConfig {
    /**
     * 通道id
     */
    private Long payChannelId;
    /**
     * 状态
     */
    private SwitchStatusEnum status;
    /**
     * 支付类型
     */
    private PayChannelEnum channelType;
    /**
     * 通道费率
     */
    private ChannelRate channelRate;
    /**
     * 通道支付类型限额
     */
    private ChannelQuota channelQuota;
    /**
     * 交易时间范围
     */
    private TimeRange timeRange;
    /**
     * 支付通道配置参数
     */
    private PayClientConfig payClientConfig;
}
