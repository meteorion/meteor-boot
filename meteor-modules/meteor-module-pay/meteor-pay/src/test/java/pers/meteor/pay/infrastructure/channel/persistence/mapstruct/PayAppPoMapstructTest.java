package pers.meteor.pay.infrastructure.channel.persistence.mapstruct;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pers.meteor.common.enums.SwitchStatusEnum;
import pers.meteor.pay.infrastructure.channel.persistence.po.PayAppEntity;

/**
 * @author meteor
 */
public class PayAppPoMapstructTest {

    @Test
    public void toPayChannel() {
        pers.meteor.pay.domain.channel.module.PayApp payChannel = new pers.meteor.pay.domain.channel.module.PayApp();
        payChannel.setAppId(1L);
        payChannel.setName("test channel");
        payChannel.setStatus(SwitchStatusEnum.OPEN);
        PayAppEntity payChannelPo = PayAppMapstruct.INSTANCE.toPayAppPo(payChannel);
        Assertions.assertNotNull(payChannelPo);
    }
}
