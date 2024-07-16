package pers.meteor.pay.domain.channel.service;

import pers.meteor.pay.domain.channel.module.ChannelConfig;
import pers.meteor.pay.domain.channel.module.PayChannel;
import pers.meteor.pay.domain.channel.module.PayClientConfig;
import pers.meteor.pay.domain.channel.module.valueobject.ChannelRate;

/**
 * @author meteor
 */
public interface PayChannelService {
    /**
     * 添加支付通道
     *
     * @param payChannel 通道配置参数
     * @return 通道id
     */
    Long addPayChannel(PayChannel payChannel);

    /**
     * 修改支付通道配置
     *
     * @param payChannel 支付通道配置参数
     */
    void updatePayChannel(PayChannel payChannel);

    /**
     * 新增通道配置
     *
     * @param channelConfig 支付通道配置参数
     */
    Long addChannelConfig(ChannelConfig channelConfig);

    /**
     * 修改支付通道配置
     *
     * @param channelId 通道id
     * @param channelConfig 通道配置
     */
    void updateChannelConfig(ChannelConfig channelConfig);

    /**
     * 更新客户端配置
     *
     * @param clientConfig /
     */
    void updateClientConfig(PayClientConfig clientConfig);

    /**
     * 修改通道费率配置
     * @param payChannelConfigId 通道id
     * @param channelRate 通道费率
     */
    void updateChannelRate(Long payChannelConfigId, ChannelRate channelRate);

    /**
     * 是否启用
     * @param payChannelId 通道id
     * @param enabled 是否启用
     */
    void enabled(Long payChannelId, boolean enabled);

    /**
     * 删除通道
     *
     * @param payChannelId /
     */
    void delete(Long payChannelId);

    /**
     * 删除通道配置
     *
     * @param payChannelConfigId /
     */
    void deleteChannelConfig(Long payChannelConfigId);
}
