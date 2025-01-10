package pers.meteor.pay.domain.order.module.valueobject;

import lombok.Data;

/**
 * 支付人
 * @author meteor
 */
@Data
public class Payer {
    /**
     * 用户标识
     */
    private String openId;
    /**
     * 支付客户端ip
     */
    private String clientIp;
    /**
     * 支付设备id
     */
    private String deviceId;
    /**
     * 门店信息
     */
    private Store store;

    @Data
    public static class Store {
        /**
         * 门牌号
         */
        private String doorplate;
        /**
         * 店铺名称
         */
        private String storeName;
        /**
         * 地区码
         */
        private Integer areaCode;
        /**
         * 店铺地址
         */
        private String address;
    }
}
