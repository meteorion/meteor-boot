package com.meteor.common.signature.core.service;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import com.meteor.common.signature.core.properties.CryptoProperties;
import com.meteor.common.signature.core.properties.DecryptProperties;
import com.meteor.common.signature.core.properties.EncryptProperties;
import lombok.Data;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * @author meteor
 */
@Data
public class DefaultEncryptServiceImpl implements EncryptionService {
    private final EncryptProperties encryptProperties;
    private final RSA rsa;

    public DefaultEncryptServiceImpl(CryptoProperties cryptoProperties) {
        encryptProperties = cryptoProperties.getEncrypt();
        byte[] keyBytes = SecureUtil.decode(encryptProperties.getSecretKey());
        PublicKey publicKey = SecureUtil.generatePublicKey(encryptProperties.getAlgorithm(), keyBytes);
        rsa = new RSA();
        rsa.setPublicKey(publicKey);
    }

    @Override
    public String encrypt(String data, HttpServletResponse response) {
        return rsa.encryptBase64(data, KeyType.PublicKey);
    }
}
