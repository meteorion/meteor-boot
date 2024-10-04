package pers.meteor.pay.channel.domain.service.impl;

import com.alibaba.fastjson2.JSON;
import org.junit.jupiter.api.Test;
import pers.meteor.pay.application.channel.PayService;
import pers.meteor.pay.application.channel.PayServiceFactory;
import pers.meteor.pay.application.channel.impl.PayServiceFactoryImpl;
import pers.meteor.pay.domain.channel.module.PayClient;
import pers.meteor.pay.domain.channel.module.enums.PayChannelEnum;

/**
 * @author meteor
 */
public class PayServiceFactoryImplTest {

    @Test
    public void createOrUpdatePayClient() {
        PayServiceFactory payClientFactory = new PayServiceFactoryImpl();
        PayClient payClientConfig = new PayClient();
        payClientConfig.setClientId(1L);
        payClientConfig.setChannelType(PayChannelEnum.MOCK);
        payClientFactory.createOrUpdatePayClient(payClientConfig);

        PayService payClient = payClientFactory.getPayClient(1L);
        System.out.println(JSON.toJSONString(payClient));
    }
}
