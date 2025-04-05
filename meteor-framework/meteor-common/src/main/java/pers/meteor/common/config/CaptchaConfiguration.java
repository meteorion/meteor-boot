package pers.meteor.common.config;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;
import pers.meteor.common.util.captcha.config.CaptchaProperties;
import pers.meteor.common.util.captcha.core.client.CaptchaClientFactory;
import pers.meteor.common.util.captcha.core.client.impl.CaptchaClientFactoryImpl;


/**
 * 验证码自动装配配置
 *
 * @author haoxr
 * @since 2023/11/24
 */
@EnableConfigurationProperties(CaptchaProperties.class)
@AutoConfiguration
public class CaptchaConfiguration {

    @Bean
    public CaptchaClientFactory captchaClientFactory(CaptchaProperties properties, RedisTemplate<String, String> redisService) {
        return new CaptchaClientFactoryImpl(properties, redisService);
    }
}
