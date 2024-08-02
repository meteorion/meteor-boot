package pers.meteor.pay.domain.order.module.valueobject;

import lombok.Data;

/**
 * 手续费
 *
 * @author meteor
 */
@Data
public class Fee {
    /**
     * 交易手续费
     */
    private Integer tradeFee;
    /**
     * 附加手续费
     */
    private Integer attachFee;

    public int getTotalFee() {
        return tradeFee + attachFee;
    }
}
