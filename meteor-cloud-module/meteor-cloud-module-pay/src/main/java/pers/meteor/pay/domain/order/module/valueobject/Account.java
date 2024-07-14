package pers.meteor.pay.order.domain.module.valueobject;

import lombok.Data;

/**
 * 支付/收款 账户信息
 *
 * @author meteor
 */
@Data
public class Account {
    /**
     * 账户名称
     */
    private String name;
    /**
     * 账户编号
     */
    private String accountNumber;
}
