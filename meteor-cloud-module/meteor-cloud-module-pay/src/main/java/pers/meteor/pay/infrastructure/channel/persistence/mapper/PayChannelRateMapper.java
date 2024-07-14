package pers.meteor.pay.infrastructure.channel.persistence.mapper;

import pers.meteor.mybatis.core.query.BaseMapperX;
import pers.meteor.pay.infrastructure.channel.persistence.po.PayChannelRatePo;

import java.util.List;

/**
 * @author meteor
 */
public interface PayChannelRateMapper extends BaseMapperX<PayChannelRatePo> {
    default List<PayChannelRatePo> selectPayChannelRateList(Long payChannelId) {
        return selectList(PayChannelRatePo::getPayChannelId, payChannelId);
    }

    default PayChannelRatePo selectOne(Long payChannelId, String channelType) {
        return selectOne(PayChannelRatePo::getPayChannelId, payChannelId, PayChannelRatePo::getChannelType, channelType);
    }
}
