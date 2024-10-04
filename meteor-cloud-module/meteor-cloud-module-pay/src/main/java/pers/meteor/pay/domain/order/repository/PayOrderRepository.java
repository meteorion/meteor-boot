package pers.meteor.pay.domain.order.repository;

import pers.meteor.pay.domain.order.module.PayOrder;

/**
 * @author meteor
 */
public interface PayOrderRepository {
    /**
     * 根据商户订单号查询订单
     *
     * @param appId 商户ID
     * @param merchantOrderNo   商户订单号
     * @return /
     */
    PayOrder selectByAppIdAndMerchantOrderNo(Long appId, String merchantOrderNo);

    /**
     * 创建支付订单
     *
     * @param payOrder /
     */
    void insertOrder(PayOrder payOrder);

    /**
     * 订单编号查询订单
     *
     * @param orderId /
     * @return /
     */
    PayOrder selectById(Long orderId);
}
