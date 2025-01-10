package pers.meteor.pay.application.channel;

import pers.meteor.pay.interfaces.channel.web.cmd.PayAppCreateCmd;
import pers.meteor.pay.interfaces.channel.web.cmd.PayAppUpdateCmd;
import pers.meteor.pay.interfaces.channel.web.cmd.ChannelRateUpdateCmd;
import pers.meteor.pay.interfaces.channel.web.cmd.PayChannelCreateCmd;
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
    Long createPayApp(PayAppCreateCmd createReqVo);

    /**
     * 更新支付通道
     *
     * @param updateReqVo /
     */
    void updatePayApp(PayAppUpdateCmd updateReqVo);

    /**
     * 添加支付通道配置
     *
     * @param createReqVo /
     */
    Long addPayChannelConfig(PayChannelCreateCmd createReqVo);

    /**
     * 更新支付通道配置
     *
     * @param createReqVo /
     */
    void updatePayChannelConfig(PayChannelCreateCmd createReqVo);

    /**
     * 更新支付通道配置
     *
     * @param clientConfigVo /
     */
    void updatePayClientConfig(PayClientRespVO clientConfigVo);

    /**
     * 更新支付通道费率
     *
     * @param createReqVo /
     */
    void updatePayChannelRate(ChannelRateUpdateCmd createReqVo);

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
