package pers.meteor.pay.domain.order.service;

import pers.meteor.pay.domain.order.module.PayOrder;
import pers.meteor.pay.domain.order.module.enums.PayStatusEnum;

/**
 * @author meteor
 */
public interface OrderService {
    /**
     * 创建订单
     *
     * @param payOrder /
     * @return /
     */
    PayOrder createOrder(PayOrder payOrder);

    /**
     * 修改订单状态
     *
     * @param orderNo   订单号
     * @param payStatus 订单状态
     */
    void updateOrderStatus(String orderNo, PayStatusEnum payStatus);
}
