package pers.meteor.pay.application.channel;

import pers.meteor.pay.interfaces.channel.web.vo.*;

/**
 * @author meteor
 */
public interface PayChannelAppService {
    /**
     * 创建支付通道
     *
     * @param createReqVo /
     * @return /
     */
    Long createPayChannel(PayChannelCreateReqVO createReqVo);

    /**
     * 更新支付通道
     *
     * @param updateReqVo /
     */
    void updatePayChannel(PayChannelUpdateReqVO updateReqVo);

    /**
     * 添加支付通道配置
     *
     * @param createReqVo /
     */
    Long addPayChannelConfig(PayChannelConfigReqVO createReqVo);

    /**
     * 更新支付通道配置
     *
     * @param createReqVo /
     */
    void updatePayChannelConfig(PayChannelConfigReqVO createReqVo);

    /**
     * 更新支付通道配置
     *
     * @param clientConfigVo /
     */
    void updatePayClientConfig(PayClientConfigVO clientConfigVo);

    /**
     * 更新支付通道费率
     *
     * @param createReqVo /
     */
    void updatePayChannelRate(PayChannelRateReqVO createReqVo);

    /**
     * 删除支付通道
     *
     * @param payChannelId /
     */
    void deletePayChannel(Long payChannelId);

    /**
     * 删除支付通道
     *
     * @param payChannelConfigId /
     */
    void deletePayChannelConfig(Long payChannelConfigId);
}
