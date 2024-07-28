package pers.meteor.event.core.entity;

import cn.hutool.core.util.IdUtil;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * @author meteor
 */
public interface DomainEvent {

    /**
     * 获取事件id
     *
     * @return /
     */
    default String getId() {
        return IdUtil.fastSimpleUUID();
    }

    /**
     * 获取事件类型
     *
     * @return /
     */
    DomainEventType getEventType();

    /**
     * 获取元数据：例如消息订阅时可传递topic
     *
     * @return /
     */
    default Map<String, Object> getMetadata() {
        return new HashMap<>();
    }

    /**
     * 事件版本号
     *
     * @return /
     */
    default int getVersion() {
        return 0;
    }

    /**
     * 获取事件创建时间
     *
     * @return /
     */
    default LocalDateTime getTimestamp() {
        return LocalDateTime.now();
    }
}
