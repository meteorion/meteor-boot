package pers.meteor.pay.infrastructure.channel.persistence.mapstruct;

import org.apache.commons.collections4.CollectionUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import pers.meteor.pay.domain.channel.module.ChannelConfig;
import pers.meteor.pay.domain.channel.module.PayChannel;
import pers.meteor.pay.domain.channel.module.PayClientConfig;
import pers.meteor.pay.domain.channel.module.enums.PayChannelEnum;
import pers.meteor.pay.domain.channel.module.valueobject.ChannelRate;
import pers.meteor.pay.infrastructure.channel.persistence.po.PayChannelConfigPO;
import pers.meteor.pay.infrastructure.channel.persistence.po.PayChannelPO;
import pers.meteor.pay.infrastructure.channel.persistence.po.PayClientConfigPO;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author meteor
 */
@Mapper
public interface PayChannelMapstruct {
    PayChannelMapstruct INSTANCE = Mappers.getMapper(PayChannelMapstruct.class);

    default PayChannel toPayChannel(PayChannelPO payChannelPo, List<PayChannelConfigPO> payChannelConfigPos, List<PayClientConfigPO> payClientConfigPos) {
        if (payChannelPo == null) {
            return null;
        }

        PayChannel payChannel = toPayChannel(payChannelPo);

        if (CollectionUtils.isNotEmpty(payChannelConfigPos)) {

            Map<PayChannelEnum, PayClientConfig> payClientConfigs = Optional.ofNullable(payClientConfigPos).orElse(new ArrayList<>()).stream().collect(Collectors.toMap(po -> PayChannelEnum.getByCode(po.getChannelType()), this::toPayClientConfg));

            for (PayChannelConfigPO payChannelConfigPo : payChannelConfigPos) {
                ChannelConfig channelConfig = toChannelConfig(payChannelConfigPo);
                PayChannelEnum channelType = channelConfig.getChannelType();
                if (channelType != null) {
                    PayClientConfig payClientConfig = payClientConfigs.get(channelType);
                    channelConfig.setPayClientConfig(payClientConfig);
                }
                payChannel.addChanneConfig(channelConfig);
            }
        }

        return payChannel;
    }

    @Mapping(target = "status", expression = "java(SwitchStatusEnum.ofCode(payChannelPo.getStatus()))")
    @Mapping(target = "channelConfigs", ignore = true)
    @Mapping(target = "channelQuota", ignore = true)
    @Mapping(target = "channelRates", ignore = true)
    PayChannel toPayChannel(PayChannelPO payChannelPo);

    @Mapping(target = "status", source = "status.code")
    @Mapping(target = "dailyLimit", source = "channelQuota.dailyLimit")
    @Mapping(target = "monthLimit", source = "channelQuota.monthLimit")
    @Mapping(target = "createTime", ignore = true)
    @Mapping(target = "updateTime", ignore = true)
    @Mapping(target = "creator", ignore = true)
    @Mapping(target = "updater", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    PayChannelPO toPayChannelPo(PayChannel payChannel);

    @Mapping(target = "channelConfigId", source = "configId")
    @Mapping(target = "status", expression = "java(SwitchStatusEnum.ofCode(payChannelConfigPo.getStatus()))")
    @Mapping(target = "channelType", expression = "java(PayChannelEnum.getByCode(payChannelConfigPo.getChannelType()))")
    @Mapping(target = "channelRate.channelType", expression = "java(PayChannelEnum.getByCode(payChannelConfigPo.getChannelType()))")
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
    ChannelConfig toChannelConfig(PayChannelConfigPO payChannelConfigPo);

    default ChannelConfig toChannelConfig(PayChannelConfigPO payChannelConfigPo, PayClientConfigPO payClientConfigPo) {
        ChannelConfig channelConfig = toChannelConfig(payChannelConfigPo);
        if (channelConfig == null) {
            return null;
        }
        PayClientConfig payClientConfg = toPayClientConfg(payClientConfigPo);
        channelConfig.setPayClientConfig(payClientConfg);
        return channelConfig;
    }

    @Mapping(source = "channelConfigId", target = "configId")
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
    @Mapping(target = "deleted", ignore = true)
    PayChannelConfigPO toChannelConfigPo(ChannelConfig channelConfig);

    default PayChannelConfigPO toChannelConfigPo(Long payChannelConfigId, ChannelRate channelRate) {
        PayChannelConfigPO payChannelConfigPo = new PayChannelConfigPO();
        payChannelConfigPo.setChannelConfigId(payChannelConfigId);
        payChannelConfigPo.setCostRate(channelRate.getCostRate());
        payChannelConfigPo.setMaxRate(channelRate.getMaxRate());
        payChannelConfigPo.setMaxFee(channelRate.getMaxFee());
        payChannelConfigPo.setMinRate(channelRate.getMinRate());
        payChannelConfigPo.setMinFee(channelRate.getMinFee());

        return payChannelConfigPo;
    }

    @Mapping(target = "channelType", expression = "java(PayChannelEnum.getByCode(payClientConfigPo.getChannelType()))")
    PayClientConfig toPayClientConfg(PayClientConfigPO payClientConfigPo);

    @Mapping(target = "channelType", source = "channelType.code")
    PayClientConfigPO toPayClinetConfigPo(PayClientConfig payClientConfig);
}
