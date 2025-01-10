package pers.meteor.pay.infrastructure.channel.persistence.mapper;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import pers.meteor.mybatis.core.query.BaseMapperX;
import pers.meteor.pay.infrastructure.channel.persistence.po.PayChannelPo;

import java.util.List;

/**
 * @author meteor
 */
public interface PayChannelMapper extends BaseMapperX<PayChannelPo> {

    default List<PayChannelPo> selectPayChannelList(Long appId) {
        return selectList(PayChannelPo::getAppId, appId);
    }

    default PayChannelPo selectOne(Long appId, String channelType) {
        return selectOne(PayChannelPo::getAppId, appId, PayChannelPo::getChannelType, channelType);
    }

    default void deleteByAppId(Long appId) {
        delete(Wrappers.lambdaQuery(PayChannelPo.class).eq(PayChannelPo::getAppId, appId));
    }
}
