package pers.meteor.pay.domain.channel.module.valueobject;

import lombok.Data;

/**
 * 支付通道配置
 *
 * @author meteor
 */
@Data
public class ChannelConfig {
    /**
     * 服务地址
     */
    private String serviceUrl;
    /**
     * 运用id
     */
    private String appId;
    /**
     * 签名类型
     */
    private String signType;
    /**
     * 通道秘钥
     */
    private String secretKey;
    /**
     * 签名key
     */
    private String signKey;
}
