package com.meteor.common.signature.config;

import com.meteor.common.signature.core.filter.DecryptionRequestFilter;
import com.meteor.common.signature.core.properties.CryptoProperties;
import com.meteor.common.signature.core.service.DecryptionService;
import com.meteor.common.signature.core.service.DefaultDecryptServiceImpl;
import com.meteor.common.signature.core.service.DefaultEncryptServiceImpl;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;
import javax.servlet.Filter;

/**
 * @author meteor
 */
@AutoConfiguration
public class SecureAutoConfiguration implements WebMvcConfigurer {

    @Bean
    public FilterRegistrationBean<Filter> regFilter(DecryptionService decryptionService) {
        FilterRegistrationBean<Filter> userFilter = new FilterRegistrationBean<>();
        userFilter.addUrlPatterns("/*");
        userFilter.setFilter(new DecryptionRequestFilter(decryptionService));
        return userFilter ;
    }

    @Bean
    public CryptoProperties cryptoProperties() {
        return new CryptoProperties();
    }

    @Bean
    public DefaultDecryptServiceImpl defaultDecryptService() {
        return new DefaultDecryptServiceImpl(cryptoProperties());
    }

    @Bean
    public DefaultEncryptServiceImpl defaultEncryptService() {
        return new DefaultEncryptServiceImpl(cryptoProperties());
    }
}
