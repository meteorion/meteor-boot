package pers.meteor.event.core.publisher;

import pers.meteor.event.core.entity.DomainEvent;

/**
 * 领域事件发布器
 *
 * @author meteor
 */
public interface DomainEventPublisher {

    /**
     * 消息类型
     *
     * @return /
     */
    MessageNotifyType getNotifyType();

    /**
     * 发布事件
     *
     * @param event 事件类型
     */
    void publish(DomainEvent event);
}
