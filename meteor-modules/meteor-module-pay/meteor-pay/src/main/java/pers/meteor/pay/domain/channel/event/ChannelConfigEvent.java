package pers.meteor.pay.domain.channel.event;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import pers.meteor.event.core.entity.DomainEvent;
import pers.meteor.event.core.entity.DomainEventType;

/**
 * 通道配置已更新
 *
 * @author meteor
 */
@Data
@RequiredArgsConstructor
public class ChannelConfigEvent implements DomainEvent {
    private final Long channelConfigId;
    private final DomainEventType eventType;
}
