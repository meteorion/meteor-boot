package pers.meteor.pay.application.channel.asselmber;

import org.apache.commons.collections4.CollectionUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import pers.meteor.pay.domain.channel.module.ChannelConfig;
import pers.meteor.pay.domain.channel.module.PayChannel;
import pers.meteor.pay.domain.channel.module.PayClientConfig;
import pers.meteor.pay.domain.channel.module.valueobject.ChannelQuota;
import pers.meteor.pay.domain.channel.module.valueobject.ChannelRate;
import pers.meteor.pay.infrastructure.channel.persistence.po.PayChannelConfigPo;
import pers.meteor.pay.infrastructure.channel.persistence.po.PayChannelPo;
import pers.meteor.pay.infrastructure.channel.persistence.po.PayClientConfigPo;
import pers.meteor.pay.interfaces.channel.web.vo.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author meteor
 */
@Mapper
public interface PayChannelAssembler extends BaseAssembler {
    PayChannelAssembler INSTANCE = Mappers.getMapper(PayChannelAssembler.class);

    @Mapping(target = "configs", ignore = true)
    PayChannelRespVO toPayChannelResp(PayChannelPo payChannelPo);

    PayChannelSimpleRespVO toPayChannelSimpleResp(PayChannelPo payChannelPo);

    @Mapping(target = "clientConfig", ignore = true)
    PayChannelConfigRespVO toPayChannelConfigResp(PayChannelConfigPo payChannelConfigPo);

    PayClientConfigVO toPayClientConfigResp(PayClientConfigPo payClientConfigPo);

    default PayChannelRespVO toPayChannelResp(PayChannelPo payChannelPo,
                                              List<PayChannelConfigPo> payChannelConfigPos,
                                              List<PayClientConfigPo> payClientConfigPos) {
        PayChannelRespVO payChannelResp = toPayChannelResp(payChannelPo);

        ArrayList<PayChannelConfigRespVO> channelConfigPos = new ArrayList<>();

        if (CollectionUtils.isNotEmpty(payChannelConfigPos)) {
            Map<String, PayClientConfigVO> clientConfigMap = Optional.ofNullable(payClientConfigPos)
                    .orElse(new ArrayList<>())
                    .stream()
                    .collect(Collectors.toMap(PayClientConfigPo::getChannelType, INSTANCE::toPayClientConfigResp));

            for (PayChannelConfigPo payChannelConfigPo : payChannelConfigPos) {
                PayChannelConfigRespVO payChannelConfigResp = INSTANCE.toPayChannelConfigResp(payChannelConfigPo);
                PayClientConfigVO clientConfig = clientConfigMap.get(payChannelConfigPo.getChannelType());
                payChannelConfigResp.setClientConfig(clientConfig);
                channelConfigPos.add(payChannelConfigResp);
            }
        }
        payChannelResp.setConfigs(channelConfigPos);

        return payChannelResp;
    }

    default PayChannel toPayChannel(PayChannelCreateReqVO createReqVo) {
        PayChannel payChannel = new PayChannel();
        payChannel.setName(createReqVo.getName());
        payChannel.setCode(createReqVo.getCode());

        ChannelQuota channelQuota = new ChannelQuota();
        channelQuota.setDailyLimit(createReqVo.getDailyLimit());
        channelQuota.setMonthLimit(createReqVo.getMonthLimit());
        payChannel.setChannelQuota(channelQuota);

        Optional.ofNullable(createReqVo.getConfigs()).ifPresent(configs -> {
           configs.stream().map(INSTANCE::toPayChannelConfig).forEach(payChannel::addConfig);
        });

        return payChannel;
    }

    default PayChannel toPayChannel(PayChannelUpdateReqVO updateReqVo) {
        PayChannel payChannel = new PayChannel();
        payChannel.setPayChannelId(updateReqVo.getPayChannelId());
        payChannel.setName(updateReqVo.getName());
        payChannel.setCode(updateReqVo.getCode());
        payChannel.setStatus(toSwitchStatus(updateReqVo.getStatus()));

        ChannelQuota channelQuota = new ChannelQuota();
        channelQuota.setDailyLimit(updateReqVo.getDailyLimit());
        channelQuota.setMonthLimit(updateReqVo.getMonthLimit());
        payChannel.setChannelQuota(channelQuota);

        Optional.ofNullable(updateReqVo.getConfigs()).ifPresent(configs -> {
            configs.stream().map(INSTANCE::toPayChannelConfig).forEach(payChannel::addConfig);
        });

        return payChannel;
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
    ChannelConfig toPayChannelConfig(PayChannelConfigReqVO createReqVo);

    ChannelRate toPayChannelRate(PayChannelRateReqVO createReqVo);

    PayClientConfig toPayChannelClient(PayClientConfigVO createReqVo);
}
