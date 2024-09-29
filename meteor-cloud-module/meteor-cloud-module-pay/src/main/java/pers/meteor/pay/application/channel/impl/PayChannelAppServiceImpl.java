package pers.meteor.pay.application.channel.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pers.meteor.pay.application.channel.PayChannelAppService;
import pers.meteor.pay.application.channel.asselmber.PayAppAssembler;
import pers.meteor.pay.application.channel.event.publisher.ChannelEventPublisher;
import pers.meteor.pay.domain.channel.module.PayApp;
import pers.meteor.pay.domain.channel.module.PayChannel;
import pers.meteor.pay.domain.channel.module.PayClient;
import pers.meteor.pay.domain.channel.module.valueobject.Rate;
import pers.meteor.pay.domain.channel.service.PayAppService;
import pers.meteor.pay.interfaces.channel.web.cmd.ChannelRateUpdateCmd;
import pers.meteor.pay.interfaces.channel.web.cmd.PayAppCreateCmd;
import pers.meteor.pay.interfaces.channel.web.cmd.PayAppUpdateCmd;
import pers.meteor.pay.interfaces.channel.web.cmd.PayChannelCreateCmd;
import pers.meteor.pay.interfaces.channel.web.vo.*;

/**
 * @author meteor
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PayChannelAppServiceImpl implements PayChannelAppService {
    private final PayAppAssembler payChannelAssembler = PayAppAssembler.INSTANCE;

    private final PayAppService payAppService;
    private final ChannelEventPublisher channelEventPublisher;

    @Override
    public Long createPayChannel(PayAppCreateCmd appCreateCmd) {
        PayApp payApp = payChannelAssembler.toPayApp(appCreateCmd);
        // 发布通道创建事件
        return payAppService.addPayApp(payApp);
    }

    @Override
    public void updatePayChannel(PayAppUpdateCmd appUpdateCmd) {
        PayApp payApp = payChannelAssembler.toPayApp(appUpdateCmd);
        payAppService.updatePayApp(payApp);
    }

    @Override
    public Long addPayChannelConfig(PayChannelCreateCmd channelCreateCmd) {
        PayChannel payChannel = payChannelAssembler.toPayChannel(channelCreateCmd);
        Long channelId = payAppService.addPayChannel(payChannel);
        // 发布修改事件
        channelEventPublisher.channelConfigUpdated(channelId);
        return channelId;
    }

    @Override
    public void updatePayChannelConfig(PayChannelCreateCmd channelCreateCmd) {
        PayChannel payChannel = payChannelAssembler.toPayChannel(channelCreateCmd);
        payAppService.updatePayChannel(payChannel);
        // 发布修改事件
        channelEventPublisher.channelConfigUpdated(payChannel.getChannelId());
    }

    @Override
    public void updatePayClientConfig(PayClientRespVO clientCreateCmd) {
        PayClient payClient = payChannelAssembler.toPayClient(clientCreateCmd);
        payAppService.updatePayClient(payClient);
        // 发布修改事件
        channelEventPublisher.channelConfigUpdated(payClient.getClientId());
    }

    @Override
    public void updatePayChannelRate(ChannelRateUpdateCmd rateUpdateCmd) {
        Rate channelRate = payChannelAssembler.toPayChannelRate(rateUpdateCmd);
        payAppService.updateChannelRate(rateUpdateCmd.getPayChannelId(), channelRate);
    }

    @Override
    public void deletePayChannel(Long appId) {
        payAppService.deleteApp(appId);
    }

    @Override
    public void deletePayChannelConfig(Long channelId) {
        payAppService.deletePayChannel(channelId);
    }
}
