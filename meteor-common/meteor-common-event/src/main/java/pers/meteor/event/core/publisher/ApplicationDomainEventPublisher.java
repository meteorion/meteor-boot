package pers.meteor.event.core.publisher;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import pers.meteor.event.core.entity.DomainEvent;

/**
 * @author meteor
 */
@RequiredArgsConstructor
public class ApplicationDomainEventPublisher implements DomainEventPublisher {
    private final ApplicationEventPublisher eventPublisher;

    @Override
    public MessageNotifyType getNotifyType() {
        return MessageNotifyType.APPLICATION;
    }

    @Override
    public void publish(DomainEvent event) {
        eventPublisher.publishEvent(event);
    }
}
