package pers.meteor.pay.interfaces.channel.web.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author meteor
 */
@Data
public class PayChannelConfigRespVO {
    /**
     * 配置id
     */
    private Long channelConfigId;
    /**
     * 通道id
     */
    private Long payChannelId;
    /**
     * 支付通道类型，{@link  pers.meteor.pay.domain.channel.module.enums.PayChannelEnum}
     */
    private String channelType;
    /**
     * 通道状态
     */
    private Integer status;

    /**
     * 成本费率（我方）
     */
    private BigDecimal costRate;
    /**
     * 成本手续费（我方）
     */
    private int costFee;
    /**
     * 最大费率(通道)
     */
    private BigDecimal maxRate;
    /**
     * 最低费率(通道)
     */
    private BigDecimal minRate;
    /**
     * 最大手续费(通道)
     */
    private int maxFee;
    /**
     * 最低手续费(通道)
     */
    private int minFee;

    /**
     * 日订单限额
     */
    private int dailyOrderLimit;
    /**
     * 日限额
     */
    private int dailyLimit;
    /**
     * 单笔最低金额
     */
    private int singleMinLimit;
    /**
     * 单笔最大限额
     */
    private int singleMaxLimit;

    /**
     * 开始时间
     */
    private LocalDateTime startTime;
    /**
     * 截止时间
     */
    private LocalDateTime endTime;
    /**
     * 客户端配置
     */
    private PayClientConfigVO clientConfig;
}
