package com.meteor.common.signature.core.properties;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author meteor
 */
@Data
@ConfigurationProperties(prefix = "cypto.encrypt")
public class EncryptProperties {
    /**
     * 是否开启
     */
    @Value("${cypto.encrypt.enable:true}")
    private boolean enable;

    /**
     * 加密算法
     */
    @Value("${cypto.encrypt.algorithm:RSA/ECB/PKCS1Padding}")
    private String algorithm;

    /**
     * 加密秘钥
     */
    private String secretKey;
    /**
     * 加密向量
     */
    private String iv;
    /**
     * 加密字段
     */
    @Value("${cypto.encrypt.field:data}")
    private String filed;
}
