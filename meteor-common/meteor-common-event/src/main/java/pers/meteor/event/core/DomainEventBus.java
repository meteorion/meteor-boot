package pers.meteor.event.core;

import org.apache.commons.compress.utils.Sets;
import pers.meteor.event.core.entity.DomainEvent;
import pers.meteor.event.core.entity.DomainEventType;
import pers.meteor.event.core.publisher.DomainEventPublisher;
import pers.meteor.event.core.publisher.MessageNotifyType;
import pers.meteor.event.core.subscriber.DomainEventSubscriber;

import java.util.Set;

/**
 * @author meteor
 */
public interface DomainEventBus {
    /**
     * 注册订阅者
     *
     * @param subscriber  监听
     * @param eventType 事件类型
     */
    void registerSubscriber(DomainEventSubscriber subscriber, DomainEventType eventType);

    /**
     * 取消订阅者
     *
     * @param subscriber  监听
     * @param eventType 事件类型
     */
    void unRegisterSubscriber(DomainEventSubscriber subscriber, DomainEventType eventType);

    /**
     * 注册发布者
     *
     * @param notifyType   通知类型
     * @param publisher 发布者
     */
    void registerPublisher(MessageNotifyType notifyType, DomainEventPublisher publisher);

    /**
     * 发布消息
     *
     * @param event      领域事件
     * @param notifyTypes 通知类型
     */
    void publish(DomainEvent event, Set<MessageNotifyType> notifyTypes);

    /**
     * 发布事件
     *
     * @param event 事件类型
     */
    default void publish(DomainEvent event) {
        publish(event, MessageNotifyType.DEFAULT);
    }

    /**
     * 发布事件
     *
     * @param event       领域事件
     * @param notifyTypes 通知类型
     */
    default void publish(DomainEvent event, MessageNotifyType... notifyTypes) {
        publish(event, Sets.newHashSet(notifyTypes));
    }
}
