package pers.meteor.pay.infrastructure.channel.persistence.mapper;

import pers.meteor.mybatis.core.query.BaseMapperX;
import pers.meteor.pay.domain.channel.module.PayChannel;
import pers.meteor.pay.infrastructure.channel.persistence.po.PayChannelPo;

/**
 * @author meteor
 */
public interface PayChannelMapper extends BaseMapperX<PayChannelPo> {

    /**
     * 查询通道配置
     *
     * @param channelId /
     * @return /
     */
    PayChannel selectByChannelId(Long channelId);
}
