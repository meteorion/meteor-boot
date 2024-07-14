package pers.meteor.pay.infrastructure.channel.persistence.mapstruct;

import org.apache.commons.collections4.CollectionUtils;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import pers.meteor.pay.domain.channel.module.ChannelConfig;
import pers.meteor.pay.domain.channel.module.PayChannel;
import pers.meteor.pay.domain.channel.module.PayClientConfig;
import pers.meteor.pay.domain.channel.module.enums.PayChannelEnum;
import pers.meteor.pay.domain.channel.module.valueobject.ChannelRate;
import pers.meteor.pay.infrastructure.channel.persistence.po.PayChannelConfigPo;
import pers.meteor.pay.infrastructure.channel.persistence.po.PayChannelPo;
import pers.meteor.pay.infrastructure.channel.persistence.po.PayChannelRatePo;
import pers.meteor.pay.infrastructure.channel.persistence.po.PayClientConfigPo;

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

    default PayChannel toPayChannel(PayChannelPo payChannelPo,
                            List<PayChannelConfigPo> payChannelConfigPos,
                            List<PayChannelRatePo> payChannelRatePos,
                            List<PayClientConfigPo> payClientConfigPos) {
        if (payChannelPo == null) {
            return null;
        }

        PayChannel payChannel = toPayChannel(payChannelPo);

        if (CollectionUtils.isNotEmpty(payChannelConfigPos)) {

            Map<PayChannelEnum, ChannelRate> channelRateMap = Optional.ofNullable(payChannelRatePos)
                    .orElse(new ArrayList<>())
                    .stream()
                    .collect(Collectors.toMap(po -> PayChannelEnum.getByCode(po.getChannelType()), this::toChannelRate));

            Map<PayChannelEnum, PayClientConfig> payClientConfigs = Optional.ofNullable(payClientConfigPos)
                    .orElse(new ArrayList<>())
                    .stream()
                    .collect(Collectors.toMap(po -> PayChannelEnum.getByCode(po.getChannelType()), this::toPayClientConfg));

            for (PayChannelConfigPo payChannelConfigPo : payChannelConfigPos) {
                ChannelConfig channelConfig = toChannelConfig(payChannelConfigPo);
                PayChannelEnum channelType = channelConfig.getChannelType();
                if (channelType != null) {
                    ChannelRate channelRate = channelRateMap.get(channelType);
                    channelConfig.setChannelRate(channelRate);
                    PayClientConfig payClientConfig = payClientConfigs.get(channelType);
                    channelConfig.setPayClientConfig(payClientConfig);
                }
                payChannel.addChanneConfig(channelConfig);
            }
        }

        return payChannel;
    }

    PayChannel toPayChannel(PayChannelPo payChannelPo);

    PayChannelPo toPayChannelPo(PayChannel payChannel);

    ChannelConfig toChannelConfig(PayChannelConfigPo payChannelConfigPo);

    PayChannelConfigPo toChannelConfigPo(ChannelConfig channelConfig);

    ChannelRate toChannelRate(PayChannelRatePo payChannelRatePo);

    PayChannelRatePo toChannelRatePo(ChannelRate channelRate);

    PayClientConfig toPayClientConfg(PayClientConfigPo payChannelConfigPo);

    PayClientConfigPo toPayClinetConfigPo(PayClientConfig payClientConfig);
}
