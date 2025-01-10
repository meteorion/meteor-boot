package pers.meteor.pay.application.channel.asselmber;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import pers.meteor.pay.domain.channel.module.PayApp;
import pers.meteor.pay.domain.channel.module.PayChannel;
import pers.meteor.pay.domain.channel.module.PayClient;
import pers.meteor.pay.domain.channel.module.valueobject.Quota;
import pers.meteor.pay.domain.channel.module.valueobject.Rate;
import pers.meteor.pay.infrastructure.channel.persistence.po.PayAppPo;
import pers.meteor.pay.infrastructure.channel.persistence.po.PayChannelPo;
import pers.meteor.pay.infrastructure.channel.persistence.po.PayClientPo;
import pers.meteor.pay.interfaces.channel.web.cmd.ChannelRateUpdateCmd;
import pers.meteor.pay.interfaces.channel.web.cmd.PayAppCreateCmd;
import pers.meteor.pay.interfaces.channel.web.cmd.PayAppUpdateCmd;
import pers.meteor.pay.interfaces.channel.web.cmd.PayChannelCreateCmd;
import pers.meteor.pay.interfaces.channel.web.vo.PayAppRespVO;
import pers.meteor.pay.interfaces.channel.web.vo.PayAppSimpleRespVO;
import pers.meteor.pay.interfaces.channel.web.vo.PayChannelRespVO;
import pers.meteor.pay.interfaces.channel.web.vo.PayClientRespVO;

import java.util.Optional;

/**
 * @author meteor
 */
@Mapper
public interface PayAppAssembler extends BaseAssembler {
    PayAppAssembler INSTANCE = Mappers.getMapper(PayAppAssembler.class);

    @Mapping(target = "configs", ignore = true)
    PayAppRespVO toPayAppResp(PayAppPo payAppPo);

    PayAppSimpleRespVO toPayAppSimpleResp(PayAppPo payAppPo);

    @Mapping(target = "payClient", ignore = true)
    PayChannelRespVO toPayChannelResp(PayChannelPo payChannelPo);

    PayClientRespVO toPayClientResp(PayClientPo payClientPo);

    default PayApp toPayApp(PayAppCreateCmd createReqVo) {
        PayApp payApp = new PayApp();
        payApp.setName(createReqVo.getName());
        payApp.setCode(createReqVo.getCode());

        Quota channelQuota = new Quota();
        channelQuota.setDailyLimit(createReqVo.getDailyLimit());
        channelQuota.setMonthLimit(createReqVo.getMonthLimit());
        payApp.setQuota(channelQuota);

        Optional.ofNullable(createReqVo.getConfigs()).ifPresent(configs -> {
           configs.stream().map(INSTANCE::toPayChannel).forEach(payApp::addConfig);
        });

        return payApp;
    }

    default PayApp toPayApp(PayAppUpdateCmd updateReqVo) {
        PayApp payApp = new PayApp();
        payApp.setAppId(updateReqVo.getPayChannelId());
        payApp.setName(updateReqVo.getName());
        payApp.setCode(updateReqVo.getCode());
        payApp.setStatus(toSwitchStatus(updateReqVo.getStatus()));

        Quota channelQuota = new Quota();
        channelQuota.setDailyLimit(updateReqVo.getDailyLimit());
        channelQuota.setMonthLimit(updateReqVo.getMonthLimit());
        payApp.setQuota(channelQuota);

        Optional.ofNullable(updateReqVo.getConfigs()).ifPresent(configs -> {
            configs.stream().map(INSTANCE::toPayChannel).forEach(payApp::addConfig);
        });

        return payApp;
    }

    @Mapping(target = "channelRate.channelType", source = "channelType")
    @Mapping(target = "channelRate.costRate", source = "costRate")
    @Mapping(target = "channelRate.costFee", source = "costFee")
    @Mapping(target = "channelRate.maxRate", source = "maxRate")
    @Mapping(target = "channelRate.minRate", source = "minRate")
    @Mapping(target = "channelRate.maxFee", source = "maxFee")
    @Mapping(target = "channelRate.minFee", source = "minFee")
    @Mapping(target = "channelQuota.dailyOrderLimit", source = "dailyOrderLimit")
    @Mapping(target = "channelQuota.dailyLimit", source = "dailyLimit")
    @Mapping(target = "channelQuota.singleMinLimit", source = "singleMinLimit")
    @Mapping(target = "channelQuota.singleMaxLimit", source = "singleMaxLimit")
    @Mapping(target = "timeRange.startTime", source = "startTime")
    @Mapping(target = "timeRange.endTime", source = "endTime")
    @Mapping(target = "payClientConfig", ignore = true)
    PayChannel toPayChannel(PayChannelCreateCmd createReqVo);

    Rate toPayChannelRate(ChannelRateUpdateCmd createReqVo);

    PayClient toPayClient(PayClientRespVO createReqVo);
}
