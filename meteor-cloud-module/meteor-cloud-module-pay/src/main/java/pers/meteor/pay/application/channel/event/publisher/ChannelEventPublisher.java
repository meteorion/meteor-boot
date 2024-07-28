package pers.meteor.pay.application.channel.event.publisher;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pers.meteor.event.core.DomainEventBus;
import pers.meteor.pay.domain.channel.event.ChannelConfigEvent;
import pers.meteor.pay.domain.channel.event.ChannelConfigEventType;

/**
 * @author meteor
 */
@Service
@RequiredArgsConstructor
public class ChannelEventPublisher {
    private final DomainEventBus domainEventBus;

    /**
     * 发布通道配置变更事件
     *
     * @param config /
     */
    public void channelConfigUpdated(Long channelConfigId) {
        ChannelConfigEvent configUpdated = new ChannelConfigEvent(channelConfigId, ChannelConfigEventType.UPDATED);
        domainEventBus.publish(configUpdated);
    }
}
