package pers.meteor.auth.framework.captcha.config;

import cn.hutool.captcha.generator.CodeGenerator;
import cn.hutool.captcha.generator.MathGenerator;
import cn.hutool.captcha.generator.RandomGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pers.meteor.auth.framework.captcha.core.client.CaptchaClientFactory;
import pers.meteor.auth.framework.captcha.core.client.impl.CaptchaClientFactoryImpl;
import pers.meteor.common.redis.service.RedisService;

import javax.annotation.Resource;
import java.awt.*;

/**
 * 验证码自动装配配置
 *
 * @author haoxr
 * @since 2023/11/24
 */
@Configuration
public class CaptchaConfiguration {

    @Resource
    private RedisService redisService;

    @Bean
    public CaptchaClientFactory captchaClientFactory(CaptchaProperties properties) {
        return new CaptchaClientFactoryImpl(properties, redisService);
    }
}
