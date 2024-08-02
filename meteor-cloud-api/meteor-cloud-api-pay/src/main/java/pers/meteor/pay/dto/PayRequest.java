package pers.meteor.pay.dto;

import com.sun.istack.internal.NotNull;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * 支付请求
 *
 * @author meteor
 */
@Data
public class PayRequest {
    /**
     * 交易订单号
     */
    private String tradeOrderNo;

    /**
     * 商品描述
     */
    private String productDescription;

    /**
     * 交易金额
     */
    private Long amount;

    /**
     * 支付过期时间
     */
    private LocalDateTime expireTime;

    /**
     * 附属参数
     */
    private Map<String, Object> metadata;
}
