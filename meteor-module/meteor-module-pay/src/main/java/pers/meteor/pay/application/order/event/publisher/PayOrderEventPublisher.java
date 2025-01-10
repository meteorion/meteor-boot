package pers.meteor.pay.application.order.event.publisher;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import pers.meteor.event.core.DomainEventBus;
import pers.meteor.event.core.publisher.MessageNotifyType;
import pers.meteor.pay.domain.order.event.PayOrderEvent;
import pers.meteor.pay.domain.order.event.PayOrderEventType;
import pers.meteor.pay.domain.order.module.PayOrder;

/**
 * @author 钟宗兵
 * @since 1.0.0
 */
@Service
@RequiredArgsConstructor
public class PayOrderEventPublisher {
    private final DomainEventBus domainEventBus;

    @Async
    public void payOrderCreated(PayOrder payOrder) {
        PayOrderEvent payOrderEvent = new PayOrderEvent(payOrder.getOrderNo(), PayOrderEventType.CREATED);
        domainEventBus.publish(payOrderEvent, MessageNotifyType.DEFAULT, MessageNotifyType.RABBIT_MQ);
    }

    @Async
    public void payOrderPaid(PayOrder payOrder) {
        PayOrderEvent payOrderEvent = new PayOrderEvent(payOrder.getOrderNo(), PayOrderEventType.PAID);
        domainEventBus.publish(payOrderEvent, MessageNotifyType.DEFAULT, MessageNotifyType.RABBIT_MQ);
    }

    @Async
    public void payOrderRefunded(PayOrder payOrder) {
        PayOrderEvent payOrderEvent = new PayOrderEvent(payOrder.getOrderNo(), PayOrderEventType.REFUNDED);
        domainEventBus.publish(payOrderEvent, MessageNotifyType.DEFAULT, MessageNotifyType.RABBIT_MQ);
    }

    @Async
    public void payOrderCanceled(PayOrder payOrder) {
        PayOrderEvent payOrderEvent = new PayOrderEvent(payOrder.getOrderNo(), PayOrderEventType.CANCELED);
        domainEventBus.publish(payOrderEvent, MessageNotifyType.DEFAULT, MessageNotifyType.RABBIT_MQ);
    }
}
