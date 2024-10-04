package pers.meteor.pay.domain.order.event;

import pers.meteor.event.core.entity.DomainEventType;

/**
 * @author 钟宗兵
 * @since 1.0.0
 */
public enum PayOrderEventType implements DomainEventType {
    // 支付订单事件
    CREATED,
    // 支付提交
    SUBMIT,
    // 支付成功
    PAID,
    // 退款成功
    REFUNDED,
    // 支付订单取消
    CANCELED
}
