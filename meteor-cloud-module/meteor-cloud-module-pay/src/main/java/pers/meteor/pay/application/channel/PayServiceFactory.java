package pers.meteor.pay.application.channel;

import pers.meteor.pay.domain.channel.module.PayClient;
import pers.meteor.pay.domain.channel.module.enums.PayChannelEnum;

/**
 * @author meteor
 */
public interface PayServiceFactory {
    /**
     * 获得支付客户端
     *
     * @param channelId 渠道编号
     * @return 支付客户端
     */
    PayService getPayClient(Long channelId);

    /**
     * 创建支付客户端
     *
     * @param config 支付配置
     */
    <Config extends PayClient> void createOrUpdatePayClient(Config config);

    /**
     * 注册支付客户端 Class，用于模块中实现的 PayClient
     *
     * @param channel 支付渠道的编码的枚举
     * @param payClientClass 支付客户端 class
     */
    void registerPayClientClass(PayChannelEnum channel, Class<?> payClientClass);
}
