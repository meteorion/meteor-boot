package pers.meteor.pay.cmd;

import lombok.Data;

import java.io.Serializable;
import java.util.Map;

/**
 * 支付请求参数
 * @author meteor
 */
@Data
public class PayOrderSubmitCmd implements Serializable {
    /**
     * 订单编号
     */
    private Long orderId;

    /**
     * 支付客户端id
     */
    private Long payClientId;

    /**
     * 展示模式
     */
    private String displayMode;

    /**
     * 附加数据
     */
    private Map<String, Object> attachData;
}
