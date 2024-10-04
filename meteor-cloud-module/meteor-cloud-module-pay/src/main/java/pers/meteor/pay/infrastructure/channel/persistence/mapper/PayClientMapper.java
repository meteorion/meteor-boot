package pers.meteor.pay.infrastructure.channel.persistence.mapper;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import pers.meteor.mybatis.core.query.BaseMapperX;
import pers.meteor.pay.infrastructure.channel.persistence.po.PayClientPo;

/**
 * @author meteor
 */
public interface PayClientMapper extends BaseMapperX<PayClientPo> {
    default PayClientPo selectByChannelId(Long channelId) {
        return selectOne(PayClientPo::getChannelId, channelId);
    }

    default void deleteByChannelId(Long channelId) {
        delete(Wrappers.lambdaQuery(PayClientPo.class).eq(PayClientPo::getClientId, channelId));
    }
}
