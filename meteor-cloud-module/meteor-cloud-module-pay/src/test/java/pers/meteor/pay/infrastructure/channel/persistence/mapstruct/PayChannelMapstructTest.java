package pers.meteor.pay.infrastructure.channel.persistence.mapstruct;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pers.meteor.common.core.enums.SwitchStatusEnum;
import pers.meteor.pay.domain.channel.module.PayChannel;
import pers.meteor.pay.infrastructure.channel.persistence.po.PayChannelPo;

/**
 * @author meteor
 */
public class PayChannelMapstructTest {

    @Test
    public void toPayChannel() {
        PayChannel payChannel = new PayChannel();
        payChannel.setPayChannelId(1L);
        payChannel.setName("test channel");
        payChannel.setStatus(SwitchStatusEnum.OPEN);
        PayChannelPo payChannelPo = PayChannelMapstruct.INSTANCE.toPayChannelPo(payChannel);
        Assertions.assertNotNull(payChannelPo);
    }
}
