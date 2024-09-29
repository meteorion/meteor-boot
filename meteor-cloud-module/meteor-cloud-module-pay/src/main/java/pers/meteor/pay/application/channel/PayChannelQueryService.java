package pers.meteor.pay.application.channel;

import pers.meteor.pay.interfaces.channel.web.vo.PayAppRespVO;
import pers.meteor.pay.interfaces.channel.web.vo.PayAppSimpleRespVO;

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
    List<PayAppSimpleRespVO> listPayChannel();

    /**
     * 查询支付通道详情
     *
     * @param payChannelId /
     * @return /
     */
    PayAppRespVO getPayChannel(Long payChannelId);
}
