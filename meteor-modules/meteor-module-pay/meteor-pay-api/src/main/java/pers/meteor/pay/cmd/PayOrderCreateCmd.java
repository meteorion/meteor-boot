package pers.meteor.pay.cmd;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pers.meteor.common.core.pojo.command.Command;

/**
 * @author meteor
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class PayOrderCreateCmd extends Command {
    /**
     * 支付运用
     */
    private Long appId;
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
     * 币种
     */
    private String currency;

}
