package pers.meteor.event.core.publisher;

/**
 * @author meteor
 */
public enum MessageNotifyType {
    /**
     * 内部领域消息
     */
    DEFAULT,
    /**
     * rabbitMq消息
     */
    RABBIT_MQ,
    /**
     * redis消息
     */
    REDIS,
    /**
     * 运用内消息
     */
    APPLICATION,
}
