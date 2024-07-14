package pers.meteor.pay.infrastructure.channel.persistence.mapper;

import pers.meteor.mybatis.core.query.BaseMapperX;
import pers.meteor.pay.infrastructure.channel.persistence.po.PayChannelConfigPo;

import java.util.List;

/**
 * @author meteor
 */
public interface PayChannelConfigMapper extends BaseMapperX<PayChannelConfigPo> {

    default List<PayChannelConfigPo> selectPayChannelConfigList(Long payChannelId) {
        return selectList(PayChannelConfigPo::getPayChannelId, payChannelId);
    }

    default PayChannelConfigPo selectOne(Long payChannelId, String channelType) {
        return selectOne(PayChannelConfigPo::getPayChannelId, payChannelId, PayChannelConfigPo::getChannelType, channelType);
    }
}
