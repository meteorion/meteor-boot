package pers.meteor.pay.domain.order.service;

import pers.meteor.pay.domain.order.module.PayOrder;
import pers.meteor.pay.domain.order.module.valueobject.PayRecord;
import pers.meteor.pay.domain.order.module.valueobject.PayResponse;

import java.util.List;

/**
 * @author meteor
 */
public interface PayOrderService {
    /**
     * 创建订单
     *
     * @param payOrder /
     * @return /
     */
    PayOrder createOrder(PayOrder payOrder);

    /**
     * 提交订单
     *
     * @param payOrder /
     * @return /
     */
    PayOrder submitOrder(PayOrder payOrder);

    /**
     * 修改订单状态
     *
     * @param payResponse   支付结果
     */
    void savePayResult(PayResponse payResponse);

    /**
     * 检查支付订单
     *
     * @param orderId /
     */
    PayOrder checkOrder(Long orderId);

    /**
     * 获取支付记录
     *
     * @param orderId /
     * @return /
     */
    List<PayRecord> listPayRecords(Long orderId);
}
