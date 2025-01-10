package pers.meteor.event.core.subscriber;

import pers.meteor.event.core.entity.DomainEvent;
import pers.meteor.event.core.entity.DomainEventType;

/**
 * @author meteor
 */
public interface DomainEventSubscriber {

    /**
     * 获取监听的事件类型
     *
     * @return /
     */
    DomainEventType getEventType();

    /**
     * 事件处理
     *
     * @param event /
     */
    void onEvent(DomainEvent event);

    /**
     * 排序
     *
     * @return /
     */
    default int order() {
        return 1;
    }
}
