package com.meteor.common.signature.core.service;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import com.meteor.common.signature.core.properties.EncryptProperties;
import lombok.Data;

import javax.servlet.http.HttpServletResponse;
import java.security.PublicKey;

/**
 * @author meteor
 */
@Data
public class DefaultEncryptServiceImpl implements EncryptionService {
    private final EncryptProperties encryptProperties;
    private final RSA rsa;

    public DefaultEncryptServiceImpl(EncryptProperties encryptProperties) {
        this.encryptProperties = encryptProperties;
        byte[] keyBytes = SecureUtil.decode(this.encryptProperties.getSecretKey());
        PublicKey publicKey = SecureUtil.generatePublicKey(this.encryptProperties.getAlgorithm(), keyBytes);
        rsa = new RSA();
        rsa.setPublicKey(publicKey);
    }

    @Override
    public String encrypt(String data, HttpServletResponse response) {
        return rsa.encryptBase64(data, KeyType.PublicKey);
    }
}
