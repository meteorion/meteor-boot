package pers.meteor.pay.domain.order.module;

import lombok.Data;
import pers.meteor.pay.domain.order.module.enums.CurrencyTypeEnum;
import pers.meteor.pay.domain.order.module.valueobject.Fee;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * 支付订单
 *
 * @author meteor
 */
@Data
public class PayOrder {
    /**
     * 支付订单号
     */
    private String orderNo;
    /**
     * 交易金额
     */
    private long amount;
    /**
     * 手续费
     */
    private Fee fee;
    /**
     * 币种类型
     */
    private CurrencyTypeEnum currency;
    /**
     * 付款账户ID
     */
    private Long payAccountId;
    /**
     * 收款账户ID
     */
    private Long receiveAccountId;
    /**
     * 交易通道ID
     */
    private Long tradeChannelId;
    /**
     * 商品属性
     */
    private Map<String, Object> itemDetails;
    /**
     * 交易附属参数
     */
    private Map<String, String> metadata;
    /**
     * 商户订单号
     */
    private String merchantOrderNo;
    /**
     * 通道订单号
     */
    private String channelOrderNo;
    /**
     * 下单时间
     */
    private LocalDateTime createTime;
    /**
     * 支付时间
     */
    private LocalDateTime payTime;
    /**
     * 支付结果
     */
    private String result;
    /**
     * 结算订单
     */
    private SettleOrder settleOrder;
    /**
     * 退款订单
     */
    private RefundOrder refundOrder;
}
