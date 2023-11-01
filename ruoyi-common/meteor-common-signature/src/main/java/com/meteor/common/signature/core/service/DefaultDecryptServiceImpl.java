package com.meteor.common.signature.core.service;

import cn.hutool.core.codec.Base64;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import com.meteor.common.signature.core.properties.CryptoProperties;
import com.meteor.common.signature.core.properties.DecryptProperties;
import lombok.Data;

import javax.servlet.http.HttpServletRequest;
import java.security.PrivateKey;

/**
 * @author meteor
 */
@Data
public class DefaultDecryptServiceImpl implements DecryptionService {
    private final DecryptProperties decryptProperties;
    private final RSA rsa;

    public DefaultDecryptServiceImpl(CryptoProperties cryptoProperties) {
        decryptProperties = cryptoProperties.getDecrypt();
        byte[] keyBytes = SecureUtil.decode(decryptProperties.getSecretKey());
        PrivateKey privateKey = SecureUtil.generatePrivateKey(decryptProperties.getAlgorithm(), keyBytes);
        rsa = new RSA();
        rsa.setPrivateKey(privateKey);
    }

    @Override
    public String decrypt(String encryptData, HttpServletRequest request) {
        return rsa.decryptStr(encryptData, KeyType.PrivateKey);
    }
}
