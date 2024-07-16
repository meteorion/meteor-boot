package pers.meteor.pay.domain.channel.repository;

import pers.meteor.common.core.enums.SwitchStatusEnum;
import pers.meteor.pay.domain.channel.module.ChannelConfig;
import pers.meteor.pay.domain.channel.module.PayChannel;
import pers.meteor.pay.domain.channel.module.PayClientConfig;
import pers.meteor.pay.domain.channel.module.enums.PayChannelEnum;
import pers.meteor.pay.domain.channel.module.valueobject.ChannelRate;

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
     * 更新通道状态
     *
     * @param payChannelId /
     * @param switchStatus /
     */
    void updateChannelStatus(Long payChannelId, SwitchStatusEnum switchStatus);

    /**
     * 保存通道配置
     *
     * @param channelConfig /
     */
    Long saveChannelConfig(ChannelConfig channelConfig);

    /**
     * 保存通道配置
     *
     * @param payChannelId /
     * @param payChannel /
     */
    void saveChannelConfigs(Long payChannelId, PayChannel payChannel);

    /**
     * 保存通道费率
     *
     * @param payChanneConfigId /
     * @param channelRate /
     */
    void saveChannelRate(Long payChanneConfigId, ChannelRate channelRate);

    /**
     * 保存客户端配置
     *
     * @param payChanneId /
     * @param clientConfig /
     */
    void saveClientConfig(Long payChanneId, PayClientConfig clientConfig);

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
     * 获取通道客户端配置
     *
     * @param payClientlId /
     * @return /
     */
    PayClientConfig selectPayClientConfig(Long payClientlId);

    /**
     * 获取通道配置
     *
     * @param payChannelConfigId /
     * @return /
     */
    ChannelConfig selectPayChannelConfig(Long payChannelConfigId);

    /**
     * 查询通道配置
     *
     * @param payChannelId 通道id
     * @param channelType 通道类型
     * @return /
     */
    ChannelConfig selectPayChannelConfig(Long payChannelId, PayChannelEnum channelType);

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
