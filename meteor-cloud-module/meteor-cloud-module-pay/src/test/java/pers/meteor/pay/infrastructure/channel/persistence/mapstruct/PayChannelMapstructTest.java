package pers.meteor.pay.infrastructure.channel.persistence.mapstruct;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pers.meteor.common.core.enums.SwitchStatusEnum;
import pers.meteor.pay.domain.channel.module.PayChannel;
import pers.meteor.pay.infrastructure.channel.persistence.po.PayChannelPO;

import static org.junit.jupiter.api.Assertions.*;

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
        PayChannelPO payChannelPo = PayChannelMapstruct.INSTANCE.toPayChannelPo(payChannel);
        Assertions.assertNotNull(payChannelPo);
    }
}
