package com.meteor.common.cypto.core.service;

import javax.servlet.http.HttpServletResponse;

/**
 * @author meteor
 */
public interface EncryptionService {

    default String encrypt(String data, HttpServletResponse response) {
        return data;
    }
}
