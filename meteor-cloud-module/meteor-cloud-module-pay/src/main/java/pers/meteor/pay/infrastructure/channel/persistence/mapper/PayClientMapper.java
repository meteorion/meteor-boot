package pers.meteor.pay.infrastructure.channel.persistence.mapper;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import pers.meteor.mybatis.core.query.BaseMapperX;
import pers.meteor.pay.infrastructure.channel.persistence.po.PayClientPo;

import java.util.List;

/**
 * @author meteor
 */
public interface PayClientMapper extends BaseMapperX<PayClientPo> {
    default List<PayClientPo> selectPayClientList(Long channelId) {
        return selectList(PayClientPo::getChannelId, channelId);
    }

    default PayClientPo selectOne(Long channelId, String channelType) {
        return selectOne(PayClientPo::getChannelId, channelId, PayClientPo::getChannelType, channelType);
    }

    default void deleteByAppId(Long channelId) {
        delete(Wrappers.lambdaQuery(PayClientPo.class).eq(PayClientPo::getChannelId, channelId));
    }

    default void deleteByChannelId(Long channelId) {
        delete(Wrappers.lambdaQuery(PayClientPo.class).eq(PayClientPo::getClientId, channelId));
    }
}
