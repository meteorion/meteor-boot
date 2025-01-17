package pers.meteor.pay.infrastructure.channel.persistence.mapper;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import pers.meteor.mybatis.core.query.BaseMapperX;
import pers.meteor.pay.infrastructure.channel.persistence.po.PayChannelEntity;

import java.util.List;

/**
 * @author meteor
 */
public interface PayChannelMapper extends BaseMapperX<PayChannelEntity> {

    default List<PayChannelEntity> selectPayChannelList(Long appId) {
        return selectList(PayChannelEntity::getAppId, appId);
    }

    default PayChannelEntity selectOne(Long appId, String channelType) {
        return selectOne(PayChannelEntity::getAppId, appId, PayChannelEntity::getChannelType, channelType);
    }

    default void deleteByAppId(Long appId) {
        delete(Wrappers.lambdaQuery(PayChannelEntity.class).eq(PayChannelEntity::getAppId, appId));
    }
}
