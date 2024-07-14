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
     * 最低费率
     */
    private BigDecimal minRate;
    /**
     * 最高费率
     */
    private BigDecimal maxRate;
}
