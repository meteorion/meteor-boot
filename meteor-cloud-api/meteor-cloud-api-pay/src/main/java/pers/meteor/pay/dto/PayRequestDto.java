package pers.meteor.pay.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Map;

/**
 * 支付请求参数
 * @author meteor
 */
@Data
public class PayRequestDto implements Serializable {
    /**
     * 支付客户端id
     */
    private String payClientId;
    /**
     * 用户ip
     */
    private String userIp;

    /**
     * 商户订单id
     */
    private String merchantOrderId;
    /**
     * 商品描述
     */
    private String description;
    /**
     * 订单原价
     */
    private int costPrice;
    /**
     * 订单标记
     */
    private String tag;
    /**
     * 商品小票id
     */
    private String invoiceId;

    /**
     * 订单金额
     */
    private Integer amount;
    /**
     * 手续费
     */
    private Integer fee;

    /**
     * 附加数据
     */
    private Map<String, Object> attachData;
}
