package pers.meteor.pay.domain.channel.repository;

import pers.meteor.common.enums.SwitchStatusEnum;
import pers.meteor.pay.domain.channel.module.PayApp;
import pers.meteor.pay.domain.channel.module.PayChannel;
import pers.meteor.pay.domain.channel.module.PayClient;
import pers.meteor.pay.domain.channel.module.enums.PayChannelEnum;
import pers.meteor.pay.domain.channel.module.valueobject.Rate;

import java.util.Collection;
import java.util.List;

/**
 * @author meteor
 */
public interface PayAppRepository {
    /**
     * 保存或更新通道配置
     *
     * @param payApp /
     * @return /
     */
    Long save(PayApp payApp);

    /**
     * 更新通道状态
     *
     * @param channelId /
     * @param switchStatus /
     */
    void updatePayAppStatus(Long channelId, SwitchStatusEnum switchStatus);

    /**
     * 保存通道配置
     *
     * @param payChannel /
     */
    Long savePayChannel(PayChannel payChannel);

    /**
     * 保存通道配置
     *
     * @param payChannels /
     */
    void savePayChannels(Collection<PayChannel> payChannels);

    /**
     * 保存通道费率
     *
     * @param channelId /
     * @param channelRate /
     */
    void saveRate(Long channelId, Rate channelRate);

    /**
     * 保存客户端配置
     *
     * @param payClient /
     */
    void savePayClient(PayClient payClient);

    /**
     * 获取通道配置
     * @param channelId /
     * @return /
     */
    PayApp selectById(Long channelId);

    /**
     * 通道名称获取通道
     *
     * @param name 通道名称
     * @return /
     */
    PayApp selectByName(String name);

    /**
     * 通道名称获取通道
     *
     * @param code 通道代号
     * @return /
     */
    List<PayApp> selectByCode(String code);

    /**
     * 获取通道客户端配置
     *
     * @param clientId /
     * @return /
     */
    PayClient selectPayClient(Long clientId);

    /**
     * 获取通道配置
     *
     * @param channelId /
     * @return /
     */
    PayChannel selectPayChannel(Long channelId);

    /**
     * 查询通道配置
     *
     * @param channelId 通道id
     * @param channelType 通道类型
     * @return /
     */
    PayChannel selectPayChannel(Long channelId, PayChannelEnum channelType);

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
