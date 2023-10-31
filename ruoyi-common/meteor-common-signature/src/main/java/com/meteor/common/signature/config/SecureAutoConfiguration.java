package com.meteor.common.signature.config;

import com.meteor.common.signature.core.interceptor.DecryptionInterceptor;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author meteor
 */
@AutoConfiguration
public class SecureAutoConfiguration implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new DecryptionInterceptor())
                .addPathPatterns("/**");
    }
}
