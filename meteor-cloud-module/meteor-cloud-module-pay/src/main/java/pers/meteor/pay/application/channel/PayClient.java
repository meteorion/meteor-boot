package pers.meteor.pay.application.channel;

import pers.meteor.pay.domain.order.module.PayOrder;
import pers.meteor.pay.domain.order.module.RefundOrder;
import pers.meteor.pay.domain.order.module.TransferOrder;
import pers.meteor.pay.domain.order.module.valueobject.PayResponse;
import pers.meteor.pay.domain.order.module.valueobject.RefundResponse;
import pers.meteor.pay.domain.order.module.valueobject.TransferResponse;

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
     * @param payOrder 支付请求
     * @return 支付响应
     */
    PayResponse unifiedOrder(PayOrder payOrder);

    /**
     * 解析订单回调
     *
     * @param body 回调参数
     * @return 解析结果
     */
    PayResponse parseOrderNotify(String body);

    /**
     * 查询订单
     *
     * @param payOrderNo 通道订单号
     * @return /
     */
    PayResponse getOrder(String payOrderNo);

    /**
     * 退款
     *
     * @param refundOrder 退款请求
     * @return 退款结果
     */
    RefundResponse unifiedRefund(RefundOrder refundOrder);

    /**
     * 解析退款通知
     *
     * @param body 回调参数
     * @return 退款结果
     */
    RefundResponse parseRefundNotify(String body);

    /**
     * 获得退款订单信息
     *
     * @param outTradeNo 外部订单号
     * @param outRefundNo 外部退款号
     * @return 退款订单信息
     */
    RefundResponse getRefundOrder(String outTradeNo, String outRefundNo);

    /**
     * 转账
     *
     * @param transferOrder 转账参数
     * @return 转账结果
     */
    TransferResponse unifiedTransfer(TransferOrder transferOrder);
}
