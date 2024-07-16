package pers.meteor.pay.infrastructure.channel.persistence.mapper;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import pers.meteor.mybatis.core.query.BaseMapperX;
import pers.meteor.pay.infrastructure.channel.persistence.po.PayClientConfigPO;

import java.util.List;

/**
 * @author meteor
 */
public interface PayClientConfigMapper extends BaseMapperX<PayClientConfigPO> {
    default List<PayClientConfigPO> selectPayClientConfigList(Long payChannelId) {
        return selectList(PayClientConfigPO::getPayChannelId, payChannelId);
    }

    default PayClientConfigPO selectOne(Long payChannelId, String channelType) {
        return selectOne(PayClientConfigPO::getPayChannelId, payChannelId, PayClientConfigPO::getChannelType, channelType);
    }

    default int deleteByPayChannelId(Long payChannelId) {
        return delete(Wrappers.lambdaQuery(PayClientConfigPO.class).eq(PayClientConfigPO::getPayChannelId, payChannelId));
    }

    default int deleteByChannelConfigId(Long payChannelConfigId) {
        return delete(Wrappers.lambdaQuery(PayClientConfigPO.class).eq(PayClientConfigPO::getChannelConfigId, payChannelConfigId));
    }
}
