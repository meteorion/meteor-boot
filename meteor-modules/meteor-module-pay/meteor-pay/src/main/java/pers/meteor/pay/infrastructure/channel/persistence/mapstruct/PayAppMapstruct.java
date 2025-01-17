package pers.meteor.pay.infrastructure.channel.persistence.mapstruct;

import org.apache.commons.collections4.CollectionUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import pers.meteor.pay.domain.channel.module.PayApp;
import pers.meteor.pay.domain.channel.module.PayChannel;
import pers.meteor.pay.domain.channel.module.PayClient;
import pers.meteor.pay.domain.channel.module.enums.PayChannelEnum;
import pers.meteor.pay.domain.channel.module.valueobject.Rate;
import pers.meteor.pay.infrastructure.channel.persistence.po.PayAppEntity;
import pers.meteor.pay.infrastructure.channel.persistence.po.PayChannelEntity;
import pers.meteor.pay.infrastructure.channel.persistence.po.PayClientPo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author meteor
 */
@Mapper
public interface PayAppMapstruct {
    PayAppMapstruct INSTANCE = Mappers.getMapper(PayAppMapstruct.class);

    default PayApp toPayApp(PayAppEntity payAppPo, List<PayChannelEntity> payChannelPos, List<PayClientPo> payClientPos) {
        if (payAppPo == null) {
            return null;
        }

        PayApp payApp = toPayApp(payAppPo);

        if (CollectionUtils.isNotEmpty(payChannelPos)) {

            Map<PayChannelEnum, PayClient> payClientMap = Optional.ofNullable(payClientPos).orElse(new ArrayList<>()).stream().collect(Collectors.toMap(po -> PayChannelEnum.getByCode(po.getChannelType()), this::toPayClient));

            for (PayChannelEntity payChannelPo : payChannelPos) {
                PayChannel payChannel = toPayChannel(payChannelPo);
                PayChannelEnum channelType = payChannel.getChannelType();
                if (channelType != null) {
                    PayClient payClient = payClientMap.get(channelType);
                    payChannel.setPayClient(payClient);
                }
                payApp.addPayChannel(payChannel);
            }
        }

        return payApp;
    }

    @Mapping(target = "status", expression = "java(SwitchStatusEnum.ofCode(payApp.getStatus()))")
    @Mapping(target = "channelConfigs", ignore = true)
    @Mapping(target = "channelQuota", ignore = true)
    @Mapping(target = "channelRates", ignore = true)
    PayApp toPayApp(PayAppEntity payApp);

    @Mapping(target = "status", source = "status.code")
    @Mapping(target = "dailyLimit", source = "quota.dailyLimit")
    @Mapping(target = "monthLimit", source = "quota.monthLimit")
    @Mapping(target = "createTime", ignore = true)
    @Mapping(target = "updateTime", ignore = true)
    @Mapping(target = "creator", ignore = true)
    @Mapping(target = "updater", ignore = true)
    PayAppEntity toPayAppPo(PayApp payApp);

    @Mapping(target = "status", expression = "java(SwitchStatusEnum.ofCode(payChannelPo.getStatus()))")
    @Mapping(target = "channelType", expression = "java(PayChannelEnum.getByCode(payChannelPo.getChannelType()))")
    @Mapping(target = "channelRate.channelType", expression = "java(PayChannelEnum.getByCode(payChannelPo.getChannelType()))")
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
    PayChannel toPayChannel(PayChannelEntity payChannelPo);

    default PayChannel toPayChannel(PayChannelEntity payChannelPo, PayClientPo payClientPo) {
        PayChannel payChannel = toPayChannel(payChannelPo);
        if (payChannel == null) {
            return null;
        }
        PayClient payClient = toPayClient(payClientPo);
        payChannel.setPayClient(payClient);
        return payChannel;
    }

    @Mapping(source = "status.code", target = "status")
    @Mapping(source = "channelType.code", target = "channelType")
    @Mapping(source = "channelRate.costRate", target = "costRate")
    @Mapping(source = "channelRate.costFee", target = "costFee")
    @Mapping(source = "channelRate.maxRate", target = "maxRate")
    @Mapping(source = "channelRate.minRate", target = "minRate")
    @Mapping(source = "channelRate.maxFee", target = "maxFee")
    @Mapping(source = "channelRate.minFee", target = "minFee")
    @Mapping(source = "channelQuota.dailyOrderLimit", target = "dailyOrderLimit")
    @Mapping(source = "channelQuota.dailyLimit", target = "dailyLimit")
    @Mapping(source = "channelQuota.singleMinLimit", target = "singleMinLimit")
    @Mapping(source = "channelQuota.singleMaxLimit", target = "singleMaxLimit")
    @Mapping(source = "timeRange.startTime", target = "startTime")
    @Mapping(source = "timeRange.endTime", target = "endTime")
    @Mapping(target = "createTime", ignore = true)
    @Mapping(target = "updateTime", ignore = true)
    @Mapping(target = "creator", ignore = true)
    @Mapping(target = "updater", ignore = true)
    PayChannelEntity toPayAppPo(PayChannel payChannel);

    default PayChannelEntity toPayAppPo(Long channelId, Rate channelRate) {
        PayChannelEntity payChannelPo = new PayChannelEntity();
        payChannelPo.setChannelId(channelId);
        payChannelPo.setCostRate(channelRate.getCostRate());
        payChannelPo.setMaxRate(channelRate.getMaxRate());
        payChannelPo.setMaxFee(channelRate.getMaxFee());
        payChannelPo.setMinRate(channelRate.getMinRate());
        payChannelPo.setMinFee(channelRate.getMinFee());

        return payChannelPo;
    }

    PayClient toPayClient(PayClientPo payClientPo);

    PayClientPo toPayClientPo(PayClient payClient);
}
