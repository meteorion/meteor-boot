package pers.meteor.pay.channel.domain.service.impl;

import com.alibaba.fastjson2.JSON;
import org.junit.jupiter.api.Test;
import pers.meteor.pay.application.channel.PayClientFactory;
import pers.meteor.pay.application.channel.impl.PayClientFactoryImpl;
import pers.meteor.pay.domain.channel.module.PayClient;
import pers.meteor.pay.domain.channel.module.enums.PayChannelEnum;

/**
 * @author meteor
 */
public class PayClientFactoryImplTest {

    @Test
    public void createOrUpdatePayClient() {
        PayClientFactory payClientFactory = new PayClientFactoryImpl();
        PayClient payClientConfig = new PayClient();
        payClientConfig.setClientId(1L);
        payClientConfig.setChannelType(PayChannelEnum.MOCK);
        payClientFactory.createOrUpdatePayClient(payClientConfig);

        pers.meteor.pay.application.channel.PayClient payClient = payClientFactory.getPayClient(1L);
        System.out.println(JSON.toJSONString(payClient));
    }
}
