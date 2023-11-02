package com.meteor.common.cypto.core.service;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import com.meteor.common.cypto.core.properties.DecryptProperties;
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

    public DefaultDecryptServiceImpl(DecryptProperties decryptProperties) {
        this.decryptProperties = decryptProperties;
        byte[] keyBytes = SecureUtil.decode(this.decryptProperties.getSecretKey());
        PrivateKey privateKey = SecureUtil.generatePrivateKey(this.decryptProperties.getAlgorithm(), keyBytes);
        rsa = new RSA();
        rsa.setPrivateKey(privateKey);
    }

    @Override
    public String decrypt(String encryptData, HttpServletRequest request) {
        return rsa.decryptStr(encryptData, KeyType.PrivateKey);
    }
}
