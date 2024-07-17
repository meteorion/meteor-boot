package pers.meteor.pay.application.channel.asselmber;

import org.apache.commons.collections4.CollectionUtils;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import pers.meteor.pay.domain.channel.module.ChannelConfig;
import pers.meteor.pay.domain.channel.module.PayChannel;
import pers.meteor.pay.domain.channel.module.PayClientConfig;
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
public interface PayChannelAssembler {
    PayChannelAssembler INSTANCE = Mappers.getMapper(PayChannelAssembler.class);

    PayChannelRespVO toPayChannelResp(PayChannelPo payChannelPo);

    PayChannelSimpleRespVO toPayChannelSimpleResp(PayChannelPo payChannelPo);

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

        return null;
    }

    default PayChannel toPayChannel(PayChannelUpdateReqVO updateReqVo) {
        return null;
    }

    default ChannelConfig toPayChannelConfig(PayChannelConfigReqVO createReqVo) {
        return null;
    }

    default ChannelRate toPayChannelRate(PayChannelRateReqVO createReqVo) {
        return null;
    }

    default PayClientConfig toPayChannelClient(PayClientConfigVO createReqVo) {
        return null;
    }
}
