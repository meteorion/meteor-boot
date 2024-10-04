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
    List<PayAppSimpleRespVO> listPayApp();

    /**
     * 查询支付通道详情
     *
     * @param appId /
     * @return /
     */
    PayAppRespVO getPayApp(Long appId);

    /**
     * 检查支付运用
     *
     * @param appId /
     * @return /
     */
    PayAppSimpleRespVO validPayApp(Long appId);
}
