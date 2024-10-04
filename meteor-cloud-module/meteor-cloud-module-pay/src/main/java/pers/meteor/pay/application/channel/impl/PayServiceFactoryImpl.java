package pers.meteor.pay.application.channel.impl;

import cn.hutool.core.lang.Assert;
import com.alibaba.fastjson2.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;
import pers.meteor.common.core.exception.ServiceException;
import pers.meteor.pay.adapter.channel.payclient.mock.MockPayService;
import pers.meteor.pay.application.channel.PayService;
import pers.meteor.pay.application.channel.PayServiceFactory;
import pers.meteor.pay.domain.channel.module.PayClient;
import pers.meteor.pay.domain.channel.module.enums.PayChannelEnum;

import java.lang.reflect.Constructor;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import static pers.meteor.pay.domain.channel.module.enums.PayChannelEnum.MOCK;

/**
 * @author meteor
 */
@Slf4j
@Service
public class PayServiceFactoryImpl implements PayServiceFactory {
    /**
     * 支付客户端 Map
     * key：渠道编号
     */
    private final ConcurrentMap<Long, AbstractPayService<?>> payServiceMap =  new ConcurrentHashMap<>();

    /**
     * 支付客户端 Class Map
     */
    private final Map<PayChannelEnum, Class<?>> clientClass = new ConcurrentHashMap<>();

    public PayServiceFactoryImpl() {
        // Mock 支付客户端
        clientClass.put(MOCK, MockPayService.class);
    }

    @Override
    public void registerPayClientClass(PayChannelEnum channel, Class<?> payClientClass) {
        clientClass.put(channel, payClientClass);
    }

    @Override
    public PayService getPayClient(Long channelId) {
        AbstractPayService<?> payService = payServiceMap.get(channelId);
        if (payService == null) {
            log.error("[pay-client-factory][渠道编号({}) 找不到客户端]", channelId);
        }
        return payService;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <Config extends PayClient> void createOrUpdatePayClient(Config config) {
        Long configId = config.getClientId();
        AbstractPayService<Config> payService = (AbstractPayService<Config>) payServiceMap.get(configId);
        if (payService == null) {
            payService = this.createPayClient(config);
            payService.initClient();
            payServiceMap.put(payService.getChannelId(), payService);
        } else {
            payService.refreshConfig(config);
        }
    }

    @SuppressWarnings("unchecked")
    private <Config extends PayClient> AbstractPayService<Config> createPayClient(Config config) {
        Long channelId = config.getClientId();
        String channelCode = config.getChannelType().getCode();
        PayChannelEnum channelEnum = PayChannelEnum.getByCode(channelCode);
        Assert.notNull(channelEnum, String.format("支付渠道(%s) 为空", channelCode));
        Class<?> payClientClass = clientClass.get(channelEnum);
        Assert.notNull(payClientClass, String.format("支付渠道(%s) Class 为空", channelCode));

        try {
            JSONObject metadata = Optional.ofNullable(config.getMetadata()).orElse(new JSONObject());
            metadata.put("serviceUrl", config.getServiceUrl());
            Constructor<?> constructor = ReflectionUtils.accessibleConstructor(payClientClass, Long.class, channelEnum.getConfigClass());
            return (AbstractPayService<Config>) constructor.newInstance(channelId, metadata.toJavaObject(channelEnum.getConfigClass()));
        } catch (Exception e) {
            throw new ServiceException("服务构建失败：{0}", payClientClass);
        }
    }
}
