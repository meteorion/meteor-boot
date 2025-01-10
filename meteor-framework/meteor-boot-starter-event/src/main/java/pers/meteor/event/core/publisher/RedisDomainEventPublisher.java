package pers.meteor.event.core.publisher;

import lombok.RequiredArgsConstructor;
import pers.meteor.event.core.entity.DomainEvent;
import pers.meteor.mq.redis.core.RedisMQTemplate;
import pers.meteor.mq.redis.core.pubsub.AbstractRedisChannelMessage;

/**
 * @author meteor
 */
@RequiredArgsConstructor
public class RedisDomainEventPublisher implements DomainEventPublisher {
    private final RedisMQTemplate redisMQTemplate;

    @Override
    public MessageNotifyType getNotifyType() {
        return MessageNotifyType.REDIS;
    }

    @Override
    public void publish(DomainEvent event) {
        RedisDomainEventMessage message = new RedisDomainEventMessage(event);
        redisMQTemplate.send(message);
    }

    @RequiredArgsConstructor
    private static class RedisDomainEventMessage extends AbstractRedisChannelMessage {
        private final DomainEvent event;
    }
}
