package pers.meteor.pay.domain.order.module;

import lombok.Data;
import pers.meteor.pay.domain.order.module.enums.RefundStatusEnum;
import pers.meteor.pay.domain.order.module.valueobject.RefundResponse;

/**
 * 退款订单
 *
 * @author meteor
 */
@Data
public class RefundOrder {
    /**
     * 支付订单号
     */
    private String payOrderNo;
    /**
     * 退款单号
     */
    private String rufundOrderNo;
    /**
     * 退款状态
     */
    private RefundStatusEnum refundStatus;
    /**
     * 退款原因
     */
    private String reason;

    /**
     * 支付金额，单位：分
     */
    private Integer payPrice;
    /**
     * 退款金额，单位：分
     */
    private Integer refundPrice;

    /**
     * 退款结果的 notify 回调地址
     */
    private String notifyUrl;

    /**
     * 退款结果
     */
    private RefundResponse refundResponse;
}
