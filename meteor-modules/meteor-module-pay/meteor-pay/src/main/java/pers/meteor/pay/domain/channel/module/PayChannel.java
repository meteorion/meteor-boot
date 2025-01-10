package pers.meteor.pay.domain.channel.module;

import lombok.Data;
import pers.meteor.common.enums.SwitchStatusEnum;
import pers.meteor.pay.domain.channel.module.enums.PayChannelEnum;
import pers.meteor.pay.domain.channel.module.valueobject.Quota;
import pers.meteor.pay.domain.channel.module.valueobject.Rate;
import pers.meteor.pay.domain.channel.module.valueobject.TimeRange;

/**
 * 通道配置
 *
 * @author meteor
 */
@Data
public class PayChannel {
    /**
     * 通道配置id
     */
    private Long channelId;
    /**
     * 通道id
     */
    private Long appId;
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
    private Rate channelRate;
    /**
     * 通道支付类型限额
     */
    private Quota channelQuota;
    /**
     * 交易时间范围
     */
    private TimeRange timeRange;
    /**
     * 支付通道配置参数
     */
    private PayClient payClient;
}
