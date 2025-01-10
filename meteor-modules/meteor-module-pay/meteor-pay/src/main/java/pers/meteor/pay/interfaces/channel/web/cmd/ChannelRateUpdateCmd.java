package pers.meteor.pay.interfaces.channel.web.cmd;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author meteor
 */
@Data
public class ChannelRateUpdateCmd {
    private Long payChannelId;
    /**
     * 支付通道类型，{@link  pers.meteor.pay.domain.channel.module.enums.PayChannelEnum}
     */
    private String channelType;
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
}
