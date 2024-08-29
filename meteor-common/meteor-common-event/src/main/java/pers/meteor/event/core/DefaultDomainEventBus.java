package pers.meteor.event.core;

import lombok.extern.slf4j.Slf4j;
import pers.meteor.event.core.entity.DomainEvent;
import pers.meteor.event.core.entity.DomainEventType;
import pers.meteor.event.core.publisher.DomainEventPublisher;
import pers.meteor.event.core.publisher.MessageNotifyType;
import pers.meteor.event.core.subscriber.DomainEventSubscriber;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @author meteor
 */
@Slf4j
public class DefaultDomainEventBus implements DomainEventBus {
    // 订阅者
    private final ConcurrentMap<DomainEventType, CopyOnWriteArraySet<DomainEventSubscriber>> subscribers = new ConcurrentHashMap<>();
    // 发布者
    private final Map<MessageNotifyType, DomainEventPublisher> publishers = new HashMap<>();

    @Override
    public void registerSubscriber(DomainEventSubscriber subscriber, DomainEventType eventType) {
        subscribers.computeIfAbsent(eventType, k -> new CopyOnWriteArraySet<>()).add(subscriber);
    }

    @Override
    public void unRegisterSubscriber(DomainEventSubscriber subscriber, DomainEventType eventType) {
        subscribers.computeIfAbsent(eventType, k -> new CopyOnWriteArraySet<>()).remove(subscriber);
    }

    @Override
    public void registerPublisher(MessageNotifyType notifyType, DomainEventPublisher publisher) {
        publishers.put(notifyType, publisher);
    }

    @Override
    public void publish(DomainEvent event, Set<MessageNotifyType> notifyTypes) {
        DomainEventType eventType = event.getEventType();
        log.info("publish event: {}", eventType);
        // 先执行内部订阅处理
        CopyOnWriteArraySet<DomainEventSubscriber> eventSubscribers = subscribers.getOrDefault(eventType, new CopyOnWriteArraySet<>());
        eventSubscribers.stream()
                .sorted(Comparator.comparingInt(DomainEventSubscriber::order))
                .forEach(subscriber -> subscriber.onEvent(event));
        // 处理消息订阅
        for (MessageNotifyType notifyType : notifyTypes) {
            if (MessageNotifyType.DEFAULT.equals(notifyType)) {
                continue;
            }
            DomainEventPublisher publisher = publishers.get(notifyType);
            if (publisher == null) {
                log.warn("publisher not register");
                continue;
            }
            log.info("notify event: {}", publisher.getNotifyType().name());
            publisher.publish(event);
        }
    }
}
