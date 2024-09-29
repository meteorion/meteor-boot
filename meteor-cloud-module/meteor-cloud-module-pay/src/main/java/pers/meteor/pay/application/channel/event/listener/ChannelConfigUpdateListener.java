package pers.meteor.pay.application.channel.event.listener;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import pers.meteor.event.core.entity.DomainEvent;
import pers.meteor.event.core.entity.DomainEventType;
import pers.meteor.event.core.subscriber.DomainEventSubscriber;
import pers.meteor.pay.application.channel.PayClientFactory;
import pers.meteor.pay.domain.channel.event.ChannelConfigEvent;
import pers.meteor.pay.domain.channel.event.ChannelConfigEventType;
import pers.meteor.pay.domain.channel.module.PayClient;
import pers.meteor.pay.domain.channel.repository.PayAppRepository;

/**
 * @author meteor
 */
@Component
@Getter
@RequiredArgsConstructor
public class ChannelConfigUpdateListener implements DomainEventSubscriber {
    private final DomainEventType eventType  = ChannelConfigEventType.UPDATED;

    private final PayClientFactory payClientFactory;
    private final PayAppRepository payChannelRepository;

    @Async
    @Override
    public void onEvent(DomainEvent event) {
        ChannelConfigEvent channelConfigEvent = (ChannelConfigEvent) event;
        PayClient payClientConfig = payChannelRepository.selectPayClient(channelConfigEvent.getChannelConfigId());
        payClientFactory.createOrUpdatePayClient(payClientConfig);
    }
}
