package pers.meteor.pay.application.channel.asselmber;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import pers.meteor.pay.domain.channel.module.ChannelConfig;
import pers.meteor.pay.domain.channel.module.PayChannel;
import pers.meteor.pay.domain.channel.module.PayClientConfig;
import pers.meteor.pay.domain.channel.module.valueobject.ChannelRate;
import pers.meteor.pay.infrastructure.channel.persistence.po.PayChannelConfigPO;
import pers.meteor.pay.infrastructure.channel.persistence.po.PayChannelPO;
import pers.meteor.pay.infrastructure.channel.persistence.po.PayClientConfigPO;
import pers.meteor.pay.interfaces.channel.vo.*;

import java.util.List;

/**
 * @author meteor
 */
@Mapper
public interface PayChannelAssembler {
    PayChannelAssembler INSTANCE = Mappers.getMapper(PayChannelAssembler.class);

    PayChannelRespVO toPayChannelResp(PayChannelPO payChannelPo);

    PayChannelSimpleRespVO toPayChannelSimpleResp(PayChannelPO payChannelPo);

    default PayChannelRespVO toPayChannelResp(PayChannelPO payChannelPo,
                                              List<PayChannelConfigPO> payChannelConfigPos,
                                              List<PayClientConfigPO> payClientConfigPos) {
        PayChannelRespVO payChannelResp = toPayChannelResp(payChannelPo);


        return null;
    }

    PayChannel toPayChannel(PayChannelCreateReqVO createReqVo);

    PayChannel toPayChannel(PayChannelUpdateReqVO updateReqVo);

    ChannelConfig toPayChannelConfig(PayChannelConfigReqVO createReqVo);

    ChannelRate toPayChannelRate(PayChannelRateReqVO createReqVo);

    PayClientConfig toPayChannelClient(PayClientConfigVO createReqVo);
}
