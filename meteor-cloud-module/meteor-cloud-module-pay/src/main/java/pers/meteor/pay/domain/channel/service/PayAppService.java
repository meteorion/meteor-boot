package pers.meteor.pay.domain.channel.service;

import pers.meteor.pay.domain.channel.module.PayApp;
import pers.meteor.pay.domain.channel.module.PayChannel;
import pers.meteor.pay.domain.channel.module.PayClient;
import pers.meteor.pay.domain.channel.module.valueobject.Rate;

/**
 * @author meteor
 */
public interface PayAppService {
    /**
     * 添加支付通道
     *
     * @param payApp 通道配置参数
     * @return 通道id
     */
    Long addPayApp(PayApp payApp);

    /**
     * 修改支付通道配置
     *
     * @param payApp 支付通道配置参数
     */
    void updatePayApp(PayApp payApp);

    /**
     * 新增通道配置
     *
     * @param payChannel 支付通道配置参数
     */
    Long addPayChannel(PayChannel payChannel);

    /**
     * 修改支付通道配置
     *
     * @param channelId 通道id
     * @param channelConfig 通道配置
     */
    void updatePayChannel(PayChannel channelConfig);

    /**
     * 更新客户端配置
     *
     * @param payClient /
     */
    void updatePayClient(PayClient payClient);

    /**
     * 修改通道费率配置
     * @param channelId 通道id
     * @param channelRate 通道费率
     */
    void updateChannelRate(Long channelId, Rate channelRate);

    /**
     * 是否启用
     * @param appId 通道id
     * @param enabled 是否启用
     */
    void enabled(Long appId, boolean enabled);

    /**
     * 删除通道
     *
     * @param appId /
     */
    void deleteApp(Long appId);

    /**
     * 删除通道配置
     *
     * @param channelId /
     */
    void deletePayChannel(Long channelId);
}
