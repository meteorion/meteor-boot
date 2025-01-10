package pers.meteor.pay.domain.order.module;

import lombok.Data;
import pers.meteor.pay.domain.order.module.enums.CurrencyTypeEnum;
import pers.meteor.pay.domain.order.module.enums.PayStatusEnum;
import pers.meteor.pay.domain.order.module.enums.TransferStatusEnum;
import pers.meteor.pay.domain.order.module.enums.TransferTypeEnum;
import pers.meteor.pay.domain.order.module.valueobject.*;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * 支付订单
 *
 * @author meteor
 */
@Data
public class TransferOrder {
    /**
     * 支付订单号
     */
    private String transferOrderNo;
    /**
     * 商户订单号
     */
    private String merchantOrderNo;
    /**
     * 转账状态
     */
    private TransferStatusEnum transferStatus;
    /**
     * 转账类型
     */
    private TransferTypeEnum transferType;

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
     * 收款人信息
     */
    private Payee payee;
    /**
     * 转账标题
     */
    private String title;
    /**
     * 回调地址
     */
    private String notifyUrl;
    /**
     * 转账附属参数
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
