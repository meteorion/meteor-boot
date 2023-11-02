package com.meteor.common.cypto.config;

import com.meteor.common.cypto.core.filter.DecryptionRequestFilter;
import com.meteor.common.cypto.core.properties.DecryptProperties;
import com.meteor.common.cypto.core.properties.EncryptProperties;
import com.meteor.common.cypto.core.service.DecryptionService;
import com.meteor.common.cypto.core.service.DefaultDecryptServiceImpl;
import com.meteor.common.cypto.core.service.DefaultEncryptServiceImpl;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.Filter;

/**
 * @author meteor
 */
@AutoConfiguration
@EnableConfigurationProperties({DecryptProperties.class, EncryptProperties.class})
public class SecureAutoConfiguration implements WebMvcConfigurer {

    @Bean
    public FilterRegistrationBean<Filter> regFilter(DecryptionService decryptionService) {
        FilterRegistrationBean<Filter> userFilter = new FilterRegistrationBean<>();
        userFilter.addUrlPatterns("/*");
        userFilter.setFilter(new DecryptionRequestFilter(decryptionService));
        return userFilter ;
    }

    @Bean
    public DefaultDecryptServiceImpl defaultDecryptService(DecryptProperties decryptProperties) {
        return new DefaultDecryptServiceImpl(decryptProperties);
    }

//    @Bean
    public DefaultEncryptServiceImpl defaultEncryptService(EncryptProperties encryptProperties) {
        return new DefaultEncryptServiceImpl(encryptProperties);
    }
}
