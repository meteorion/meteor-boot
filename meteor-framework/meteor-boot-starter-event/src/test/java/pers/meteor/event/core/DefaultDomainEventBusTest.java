package pers.meteor.event.core;


import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pers.meteor.event.core.entity.DomainEvent;
import pers.meteor.event.core.entity.DomainEventType;
import pers.meteor.event.core.subscriber.DomainEventSubscriber;

/**
 * @author meteor
 */
@Slf4j
public class DefaultDomainEventBusTest {

    private DomainEventBus domainEventBus;

    @BeforeEach
    public void init() {
        domainEventBus = new DefaultDomainEventBus();

        domainEventBus.registerSubscriber(new DomainEventSubscriber() {
            @Override
            public DomainEventType getEventType() {
                return TestDomainEventType.TEST;
            }

            @Override
            public void onEvent(DomainEvent event) {
                log.info("test");
            }
        }, TestDomainEventType.TEST);
    }

    @Test
    public void publish() {
        domainEventBus.publish(new DomainEvent() {
            @Override
            public DomainEventType getEventType() {
                return TestDomainEventType.TEST;
            }

            @Override
            public int getVersion() {
                return 0;
            }
        });
    }

    public enum TestDomainEventType implements DomainEventType {
        TEST
    }
}
