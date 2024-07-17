package pers.meteor.pay.infrastructure.channel.persistence.mapper;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import pers.meteor.mybatis.core.query.BaseMapperX;
import pers.meteor.pay.infrastructure.channel.persistence.po.PayClientConfigPo;

import java.util.List;

/**
 * @author meteor
 */
public interface PayClientConfigMapper extends BaseMapperX<PayClientConfigPo> {
    default List<PayClientConfigPo> selectPayClientConfigList(Long payChannelId) {
        return selectList(PayClientConfigPo::getPayChannelId, payChannelId);
    }

    default PayClientConfigPo selectOne(Long payChannelId, String channelType) {
        return selectOne(PayClientConfigPo::getPayChannelId, payChannelId, PayClientConfigPo::getChannelType, channelType);
    }

    default int deleteByPayChannelId(Long payChannelId) {
        return delete(Wrappers.lambdaQuery(PayClientConfigPo.class).eq(PayClientConfigPo::getPayChannelId, payChannelId));
    }

    default int deleteByChannelConfigId(Long payChannelConfigId) {
        return delete(Wrappers.lambdaQuery(PayClientConfigPo.class).eq(PayClientConfigPo::getChannelConfigId, payChannelConfigId));
    }
}
