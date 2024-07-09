package pers.meteor.pay.channel.domain.service;

import pers.meteor.pay.channel.domain.module.PayChannel;
import pers.meteor.pay.channel.domain.module.enums.RateTypeEnum;
import pers.meteor.pay.channel.domain.module.PayClientConfig;
import pers.meteor.pay.channel.domain.module.valueobject.ChannelRate;

import java.util.EnumMap;

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
     * 修改支付通道配置
     *
     * @param channelId 通道id
     * @param channelConfig 通道配置
     */
    void updateChannelConfig(Long channelId, PayClientConfig channelConfig);

    /**
     * 修改通道费率配置
     * @param channelId 通道id
     * @param channelRates 通道费率
     */
    void updateChannelRate(Long channelId, EnumMap<RateTypeEnum, ChannelRate> channelRates);

    /**
     * 是否启用
     * @param channelId 通道id
     * @param enabled 是否启用
     */
    void enabled(Long channelId, boolean enabled);
}
