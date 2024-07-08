package pers.meteor.pay.order.domain.module.valueobject;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 通道信息：冗余手续费
 *
 * @author meteor
 */
@Data
public class Channel {
    /**
     * 通道编号
     */
    private String code;
    /**
     * 通道名称
     */
    private String name;
    /**
     * 成本有续费
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
