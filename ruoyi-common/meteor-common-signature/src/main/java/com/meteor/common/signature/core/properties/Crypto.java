package com.meteor.common.signature.core.properties;

import lombok.Data;

/**
 * @author meteor
 */
@Data
public class Crypto {
    /**
     * 是否开启
     */
    private boolean enable;
    /**
     * 加密算法
     */
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
    private String filed;
}
