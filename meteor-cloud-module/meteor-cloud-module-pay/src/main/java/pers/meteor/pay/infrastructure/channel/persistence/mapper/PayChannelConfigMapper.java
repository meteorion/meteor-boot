package pers.meteor.pay.infrastructure.channel.persistence.mapper;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import pers.meteor.mybatis.core.query.BaseMapperX;
import pers.meteor.pay.infrastructure.channel.persistence.po.PayChannelConfigPO;

import java.util.List;

/**
 * @author meteor
 */
public interface PayChannelConfigMapper extends BaseMapperX<PayChannelConfigPO> {

    default List<PayChannelConfigPO> selectPayChannelConfigList(Long payChannelId) {
        return selectList(PayChannelConfigPO::getPayChannelId, payChannelId);
    }

    default PayChannelConfigPO selectOne(Long payChannelId, String channelType) {
        return selectOne(PayChannelConfigPO::getPayChannelId, payChannelId, PayChannelConfigPO::getChannelType, channelType);
    }

    default int deleteByPayChannelId(Long payChannelId) {
        return delete(Wrappers.lambdaQuery(PayChannelConfigPO.class).eq(PayChannelConfigPO::getPayChannelId, payChannelId));
    }
}
