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
    private CaptchaProperties captchaProperties;
    @Resource
    private RedisService redisService;

    /**
     * 验证码文字生成器
     *
     * @return CodeGenerator
     */
    @Bean
    public CodeGenerator codeGenerator() {
        String codeType = captchaProperties.getCode().getType();
        int codeLength = captchaProperties.getCode().getLength();
        if ("math".equalsIgnoreCase(codeType)) {
            return new MathGenerator(codeLength);
        } else if ("random".equalsIgnoreCase(codeType)) {
            return new RandomGenerator(codeLength);
        } else {
            throw new IllegalArgumentException("Invalid captcha codegen type: " + codeType);
        }
    }

    /**
     * 验证码字体
     */
    @Bean
    public Font captchaFont() {
        String fontName = captchaProperties.getFont().getName();
        int fontSize = captchaProperties.getFont().getSize();
        int fontWight = captchaProperties.getFont().getWeight();
        return new Font(fontName, fontWight, fontSize);
    }

    @Bean
    public CaptchaClientFactory captchaClientFactory(CaptchaProperties properties, CodeGenerator codeGenerator, Font captchaFont) {
        return new CaptchaClientFactoryImpl(properties, redisService, codeGenerator, captchaFont);
    }
}
