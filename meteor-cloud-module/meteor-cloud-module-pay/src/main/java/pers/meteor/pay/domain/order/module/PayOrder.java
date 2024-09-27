package pers.meteor.pay.domain.order.module;

import lombok.Data;
import pers.meteor.pay.domain.order.module.enums.CurrencyTypeEnum;
import pers.meteor.pay.domain.order.module.enums.DisplayModeEnum;
import pers.meteor.pay.domain.order.module.enums.PayStatusEnum;
import pers.meteor.pay.domain.order.module.valueobject.Fee;
import pers.meteor.pay.domain.order.module.valueobject.Goods;
import pers.meteor.pay.domain.order.module.valueobject.PayResponse;
import pers.meteor.pay.domain.order.module.valueobject.Payer;

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
     * 商户订单号
     */
    private String merchantOrderNo;
    /**
     * 交易通道ID
     */
    private Long tradeChannelId;
    /**
     * 订单状态
     */
    private PayStatusEnum payStatus;

    /**
     * 交易金额
     */
    private Integer amount;
    /**
     * 手续费
     */
    private Fee fee;
    /**
     * 币种类型
     */
    private CurrencyTypeEnum currency;
    /**
     * 付款人信息
     */
    private Payer payer;
    /**
     * 商品属性
     */
    private Goods goods;
    /**
     * 回调地址
     */
    private String notifyUrl;
    /**
     * 响应地址
     */
    private String returnUrl;
    /**
     * 展示样式
     */
    private DisplayModeEnum displayMode;
    /**
     * 交易附属参数
     */
    private Map<String, String> metadata;

    /**
     * 下单时间
     */
    private LocalDateTime createTime;
    /**
     * 失效时间
     */
    private LocalDateTime expireTime;

    /**
     * 支付结果
     */
    private PayResponse payResponse;

    /**
     * 结算订单
     */
    private SettleOrder settleOrder;
    /**
     * 退款订单
     */
    private RefundOrder refundOrder;

    public String getMetadata(String key) {
        return metadata != null ? metadata.get(key) : null;
    }
}
