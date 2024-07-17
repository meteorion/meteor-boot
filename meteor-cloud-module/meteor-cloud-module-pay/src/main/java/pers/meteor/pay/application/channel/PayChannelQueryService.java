package pers.meteor.pay.application.channel;

import pers.meteor.pay.interfaces.channel.web.vo.PayChannelRespVO;
import pers.meteor.pay.interfaces.channel.web.vo.PayChannelSimpleRespVO;

import java.util.List;

/**
 * @author meteor
 */
public interface PayChannelQueryService {
    /**
     * 查询通道列表
     *
     * @return /
     */
    List<PayChannelSimpleRespVO> listPayChannel();

    /**
     * 查询支付通道详情
     *
     * @param payChannelId /
     * @return /
     */
    PayChannelRespVO getPayChannel(Long payChannelId);
}
