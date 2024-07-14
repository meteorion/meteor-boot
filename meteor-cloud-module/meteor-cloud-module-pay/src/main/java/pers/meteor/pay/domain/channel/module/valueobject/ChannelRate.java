package pers.meteor.pay.domain.channel.module.valueobject;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 通道费率
 *
 * @author meteor
 */
@Data
public class ChannelRate {
    /**
     * 成本费率
     */
    private BigDecimal costRate;
    /**
     * 成本手续费
     */
    private long costFee;
}
