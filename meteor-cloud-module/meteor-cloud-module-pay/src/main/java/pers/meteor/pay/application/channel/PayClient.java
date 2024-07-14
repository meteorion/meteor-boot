package pers.meteor.pay.application.channel;

import pers.meteor.pay.dto.*;

import java.util.Map;

/**
 * @author meteor
 */
public interface PayClient {
    /**
     * 获取客户端id
     *
     * @return /
     */
    Long getChannelId();

    /**
     * 下单
     *
     * @param payRequest 支付请求
     * @return 支付响应
     */
    PayResponse createOrder(PayRequest payRequest);

    /**
     * 解析订单回调
     *
     * @param params 回调参数
     * @return 解析结果
     */
    PayResponse parseOrderNotify(Map<String, String> params);

    /**
     * 查询订单
     *
     * @param outTradeNo 通道订单号
     * @return /
     */
    PayResponse queryOrder(String outTradeNo);

    /**
     * 退款
     *
     * @param refundRequest 退款请求
     * @return 退款结果
     */
    RefundResponse refund(RefundRequest refundRequest);

    /**
     * 解析退款通知
     *
     * @param params 回调参数
     * @return 退款结果
     */
    RefundResponse parseRefundResponse(Map<String, String> params);

    /**
     * 转账
     *
     * @param transferRequest 转账参数
     * @return 转账结果
     */
    TransferResponse transfer(TransferRequest transferRequest);
}
