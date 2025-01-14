package pers.meteor.web.core.crypto;

import java.util.HashMap;

/**
 * @author 钟宗兵
 * @since 1.0.0
 */
public interface CryptoService {
    CryptoConfig getCryptoConfig(String agentId);

    String decrypt(String data, String decryptKey);

    String encrypt(String data, String encryptKey);

    boolean compareSignature(HashMap<String, Object> data, String signKey, String signature);
}
