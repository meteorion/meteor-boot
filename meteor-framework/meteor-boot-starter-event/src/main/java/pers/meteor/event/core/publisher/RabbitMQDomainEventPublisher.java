package pers.meteor.event.core.publisher;

import pers.meteor.event.core.entity.DomainEvent;

/**
 * @author meteor
 */
public class RabbitMQDomainEventPublisher implements DomainEventPublisher {

    @Override
    public MessageNotifyType getNotifyType() {
        return MessageNotifyType.RABBIT_MQ;
    }

    @Override
    public void publish(DomainEvent event) {

    }
}
