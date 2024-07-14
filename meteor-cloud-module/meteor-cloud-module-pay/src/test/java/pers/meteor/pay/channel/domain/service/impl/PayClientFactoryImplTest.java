package pers.meteor.pay.channel.domain.service.impl;

import com.alibaba.fastjson2.JSON;
import org.junit.jupiter.api.Test;
import pers.meteor.pay.domain.channel.module.PayClientConfig;
import pers.meteor.pay.domain.channel.module.enums.PayChannelEnum;
import pers.meteor.pay.domain.channel.service.PayClient;
import pers.meteor.pay.domain.channel.service.PayClientFactory;
import pers.meteor.pay.domain.channel.service.impl.PayClientFactoryImpl;

/**
 * @author meteor
 */
public class PayClientFactoryImplTest {

    @Test
    public void createOrUpdatePayClient() {
        PayClientFactory payClientFactory = new PayClientFactoryImpl();
        PayClientConfig payClientConfig = new PayClientConfig();
        payClientConfig.setConfigId(1L);
        payClientConfig.setPayChannel(PayChannelEnum.MOCK);
        payClientFactory.createOrUpdatePayClient(payClientConfig);

        PayClient payClient = payClientFactory.getPayClient(1L);
        System.out.println(JSON.toJSONString(payClient));
    }
}
