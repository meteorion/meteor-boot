package pers.meteor.pay.channel.domain.service.impl;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ReflectUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pers.meteor.pay.channel.adapter.payclient.mock.MockPayClient;
import pers.meteor.pay.channel.domain.module.PayClientConfig;
import pers.meteor.pay.channel.domain.module.enums.PayChannelEnum;
import pers.meteor.pay.channel.domain.service.PayClient;
import pers.meteor.pay.channel.domain.service.PayClientFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import static pers.meteor.pay.channel.domain.module.enums.PayChannelEnum.MOCK;

/**
 * @author meteor
 */
@Slf4j
@Service
public class PayClientFactoryImpl implements PayClientFactory {
    /**
     * 支付客户端 Map
     * key：渠道编号
     */
    private final ConcurrentMap<Long, AbstractPayClient<?>> clients = new ConcurrentHashMap<>();

    /**
     * 支付客户端 Class Map
     */
    private final Map<PayChannelEnum, Class<?>> clientClass = new ConcurrentHashMap<>();

    public PayClientFactoryImpl() {
        // Mock 支付客户端
        clientClass.put(MOCK, MockPayClient.class);
    }

    @Override
    public void registerPayClientClass(PayChannelEnum channel, Class<?> payClientClass) {
        clientClass.put(channel, payClientClass);
    }

    @Override
    public PayClient getPayClient(Long channelId) {
        AbstractPayClient<?> client = clients.get(channelId);
        if (client == null) {
            log.error("[pay-client-factory][渠道编号({}) 找不到客户端]", channelId);
        }
        return client;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <Config extends PayClientConfig> void createOrUpdatePayClient(Long channelId, String channelCode,
                                                                         Config config) {
        AbstractPayClient<Config> client = (AbstractPayClient<Config>) clients.get(channelId);
        if (client == null) {
            client = this.createPayClient(channelId, channelCode, config);
            client.initClient();
            clients.put(client.getChannelId(), client);
        } else {
            client.refreshConfig(config);
        }
    }

    @SuppressWarnings("unchecked")
    private <Config extends PayClientConfig> AbstractPayClient<Config> createPayClient(Long channelId, String channelCode,
                                                                                       Config config) {
        PayChannelEnum channelEnum = PayChannelEnum.getByCode(channelCode);
        Assert.notNull(channelEnum, String.format("支付渠道(%s) 为空", channelCode));
        Class<?> payClientClass = clientClass.get(channelEnum);
        Assert.notNull(payClientClass, String.format("支付渠道(%s) Class 为空", channelCode));
        return (AbstractPayClient<Config>) ReflectUtil.newInstance(payClientClass, channelId, config);
    }

}
