package pers.meteor.pay.domain.order.module.valueobject;

import lombok.Data;

import java.util.List;

/**
 * 商品信息
 * @author meteor
 */
@Data
public class Goods {
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
     * 商品详情
     */
    private List<GoodsDetail> details;
}
