package pers.meteor.pay.domain.order.module;

import lombok.Data;
import pers.meteor.pay.domain.order.module.enums.OrderStatusEnum;

/**
 * 退款订单信息(聚合)
 *
 * @author meteor
 */
@Data
public class OrderInfo {
    /**
     * 支付订单号
     */
    private String orderNo;
    /**
     * 订单状态
     */
    private OrderStatusEnum orderStatus;
    /**
     * 支付订单信息
     */
    private PayOrder payOrder;
    /**
     * 结算订单信息
     */
    private SettleOrder settleOrder;
    /**
     * 退款订单
     */
    private RefundOrder refundOrder;
}
