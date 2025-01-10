package pers.meteor.event.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import pers.meteor.event.core.DefaultDomainEventBus;
import pers.meteor.event.core.DomainEventBus;
import pers.meteor.event.core.entity.DomainEventType;
import pers.meteor.event.core.publisher.*;
import pers.meteor.event.core.subscriber.DomainEventSubscriber;
import pers.meteor.mq.redis.core.RedisMQTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author meteor
 */
@AutoConfiguration
@Slf4j
public class DomainEventAutoConfiguration {

    @Bean
    public ApplicationDomainEventPublisher getApplicationDomainEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        return new ApplicationDomainEventPublisher(applicationEventPublisher);
    }

    @Bean
    @ConditionalOnClass(name = "org.springframework.amqp.rabbit.core.RabbitTemplate")
    public RabbitMQDomainEventPublisher getRabbitMQDomainEventPublisher() {
        return new RabbitMQDomainEventPublisher();
    }

    @Bean
    @ConditionalOnProperty(value = "mq.redis.enabled", havingValue = "true")
    public RedisDomainEventPublisher getRedisDomainEventPublisher(RedisMQTemplate redisMQTemplate) {
        return new RedisDomainEventPublisher(redisMQTemplate);
    }

    @Bean
    public DomainEventBus getDomainEventBus(List<DomainEventPublisher> publishers, List<DomainEventSubscriber> subscribers) {

        DefaultDomainEventBus defaultDomainEventBus = new DefaultDomainEventBus();

        // 注册发布者
        for (DomainEventPublisher publisher : Optional.ofNullable(publishers).orElse(new ArrayList<>())) {
            MessageNotifyType notifyType = publisher.getNotifyType();
            if (notifyType == null) {
                throw new IllegalArgumentException("notify type can not be null");
            }
            defaultDomainEventBus.registerPublisher(notifyType, publisher);
        }
        // 注册订阅者
        for (DomainEventSubscriber subscriber : subscribers) {
            DomainEventType eventType = subscriber.getEventType();
            if (eventType == null) {
                throw new IllegalArgumentException("event type can not be null");
            }
            defaultDomainEventBus.registerSubscriber(subscriber, eventType);
        }

        return defaultDomainEventBus;
    }
}
