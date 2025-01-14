package pers.meteor.web.core.crypto;

import lombok.Data;

/**
 * @author 钟宗兵
 * @since 1.0.0
 */
@Data
public class CryptoConfig {
    private String agentId;
    private String decryptKey;
    private String encryptKey;
    private String signKey;
}
