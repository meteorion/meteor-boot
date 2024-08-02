package pers.meteor.pay.domain.order.module.valueobject;

import lombok.Data;

/**
 * 收款人
 *
 * @author meteor
 */
@Data
public class Payee {
    /**
     * 用户标识
     */
    private String openId;
}
