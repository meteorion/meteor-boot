package pers.meteor.pay.domain.order.event;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import pers.meteor.event.core.entity.DomainEvent;
import pers.meteor.event.core.entity.DomainEventType;

/**
 * @author 钟宗兵
 * @since 1.0.0
 */
@Data
@RequiredArgsConstructor
public class PayOrderEvent implements DomainEvent {
    private final String orderNo;
    private final DomainEventType eventType;
}
