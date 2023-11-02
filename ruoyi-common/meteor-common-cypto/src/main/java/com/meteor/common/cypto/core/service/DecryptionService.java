package com.meteor.common.cypto.core.service;

import com.meteor.common.cypto.core.properties.DecryptProperties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author meteor
 */
public interface DecryptionService {

    default boolean support(HttpServletRequest request) throws ServletException {
        return true;
    }

    DecryptProperties getDecryptProperties();

    String decrypt(String encryptData, HttpServletRequest request) throws ServletException, IOException;
}
