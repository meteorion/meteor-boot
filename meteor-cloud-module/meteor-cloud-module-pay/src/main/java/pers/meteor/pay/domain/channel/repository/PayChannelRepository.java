package pers.meteor.pay.domain.channel.repository;

import pers.meteor.pay.domain.channel.module.PayChannel;
import pers.meteor.pay.domain.channel.module.PayClientConfig;

import java.util.List;

/**
 * @author meteor
 */
public interface PayChannelRepository {
    /**
     * 保存或更新通道配置
     *
     * @param payChannel /
     * @return /
     */
    Long save(PayChannel payChannel);

    /**
     * 获取通道配置
     * @param channelId /
     * @return /
     */
    PayChannel selectById(Long channelId);

    /**
     * 通道名称获取通道
     *
     * @param name 通道名称
     * @return /
     */
    PayChannel selectByName(String name);

    /**
     * 通道名称获取通道
     *
     * @param code 通道代号
     * @return /
     */
    List<PayChannel> selectByCode(String code);

    /**
     * 获取通道配置
     *
     * @param channelId /
     * @return /
     */
    PayClientConfig selectPayClientConfig(Long channelId);
}
