package pers.meteor.pay.application.channel.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pers.meteor.pay.application.channel.PayChannelAppService;
import pers.meteor.pay.application.channel.asselmber.PayChannelAssembler;
import pers.meteor.pay.application.channel.event.publisher.ChannelEventPublisher;
import pers.meteor.pay.domain.channel.module.ChannelConfig;
import pers.meteor.pay.domain.channel.module.PayChannel;
import pers.meteor.pay.domain.channel.module.PayClientConfig;
import pers.meteor.pay.domain.channel.module.valueobject.ChannelRate;
import pers.meteor.pay.domain.channel.service.PayChannelService;
import pers.meteor.pay.interfaces.channel.web.vo.*;

/**
 * @author meteor
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PayChannelAppServiceImpl implements PayChannelAppService {
    private final PayChannelAssembler payChannelAssembler = PayChannelAssembler.INSTANCE;

    private final PayChannelService payChannelService;
    private final ChannelEventPublisher channelEventPublisher;

    @Override
    public Long createPayChannel(PayChannelCreateReqVO createReqVo) {
        PayChannel payChannel = payChannelAssembler.toPayChannel(createReqVo);
        // 发布通道创建事件
        return payChannelService.addPayChannel(payChannel);
    }

    @Override
    public void updatePayChannel(PayChannelUpdateReqVO updateReqVo) {
        PayChannel payChannel = payChannelAssembler.toPayChannel(updateReqVo);
        payChannelService.updatePayChannel(payChannel);
    }

    @Override
    public Long addPayChannelConfig(PayChannelConfigReqVO createReqVo) {
        ChannelConfig payChannelConfig = payChannelAssembler.toPayChannelConfig(createReqVo);
        Long channelConfigId = payChannelService.addChannelConfig(payChannelConfig);
        // 发布修改事件
        channelEventPublisher.channelConfigUpdated(channelConfigId);
        return channelConfigId;
    }

    @Override
    public void updatePayChannelConfig(PayChannelConfigReqVO createReqVo) {
        ChannelConfig payChannelConfig = payChannelAssembler.toPayChannelConfig(createReqVo);
        payChannelService.updateChannelConfig(payChannelConfig);
        // 发布修改事件
        channelEventPublisher.channelConfigUpdated(payChannelConfig.getChannelConfigId());
    }

    @Override
    public void updatePayClientConfig(PayClientConfigVO clientConfigVo) {
        PayClientConfig payChannelClient = payChannelAssembler.toPayChannelClient(clientConfigVo);
        payChannelService.updateClientConfig(payChannelClient);
        // 发布修改事件
        channelEventPublisher.channelConfigUpdated(payChannelClient.getPayClientId());
    }

    @Override
    public void updatePayChannelRate(PayChannelRateReqVO createReqVo) {
        ChannelRate payChannelRate = payChannelAssembler.toPayChannelRate(createReqVo);
        payChannelService.updateChannelRate(createReqVo.getPayChannelId(), payChannelRate);
    }

    @Override
    public void deletePayChannel(Long payChannelId) {
        payChannelService.delete(payChannelId);
    }

    @Override
    public void deletePayChannelConfig(Long payChannelConfigId) {
        payChannelService.deleteChannelConfig(payChannelConfigId);
    }
}
