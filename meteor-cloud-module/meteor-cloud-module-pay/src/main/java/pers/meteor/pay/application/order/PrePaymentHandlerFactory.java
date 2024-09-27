package pers.meteor.pay.application.order;

import pers.meteor.pay.domain.order.module.PayOrder;

/**
 * 支付前置处理
 * @author 钟宗兵
 * @since 1.0.0
 */
public interface PrePaymentHandlerFactory {
    /**
     * 支付预处理
     *
     * @param payOrder /
     */
    void handle(PayOrder payOrder);
}
