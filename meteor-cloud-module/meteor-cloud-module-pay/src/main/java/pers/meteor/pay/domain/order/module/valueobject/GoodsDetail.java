package pers.meteor.pay.domain.order.module.valueobject;

import lombok.Data;

/**
 * 产品信息
 * @author 钟宗兵
 * @since 1.0.0
 */
@Data
public class GoodsDetail {
    /**
     * 商品id
     */
    private String goodsId;
    /**
     * 商品编号
     */
    private String goodsCode;
    /**
     * 商品名称
     */
    private String goodsName;
    /**
     * 商品数量
     */
    private int quantity;
    /**
     * 商品单价
     */
    private int unitPrice;
}
