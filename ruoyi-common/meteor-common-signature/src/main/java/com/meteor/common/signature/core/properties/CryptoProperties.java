package com.meteor.common.signature.core.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author meteor
 */
@Data
@ConfigurationProperties(prefix = "crypto")
public class CryptoProperties {
    /**
     * 解密配置
     */
    private EncryptProperties encrypt;
    /**
     * 加密配置
     */
    private DecryptProperties decrypt;
}

