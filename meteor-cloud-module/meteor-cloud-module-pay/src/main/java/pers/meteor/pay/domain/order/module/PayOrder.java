package pers.meteor.pay.order.domain.module;

import lombok.Data;
import pers.meteor.pay.order.domain.module.valueobject.Account;
import pers.meteor.pay.order.domain.module.valueobject.Channel;
import pers.meteor.pay.order.domain.module.valueobject.Fee;

import java.time.LocalDateTime;

/**
 * 支付订单
 *
 * @author meteor
 */
@Data
public class PayOrder {
    /**
     * 支付订单号
     */
    private String orderNo;
    /**
     * 交易金额
     */
    private long amount;
    /**
     * 手续费
     */
    private Fee fee;
    /**
     * 付款账户
     */
    private Account payAccount;
    /**
     * 收款账户
     */
    private Account receiveAccount;
    /**
     * 交易通道
     */
    private Channel tradeChannel;
    /**
     * 下单时间
     */
    private LocalDateTime createTime;
    /**
     * 支付时间
     */
    private LocalDateTime payTime;
}
