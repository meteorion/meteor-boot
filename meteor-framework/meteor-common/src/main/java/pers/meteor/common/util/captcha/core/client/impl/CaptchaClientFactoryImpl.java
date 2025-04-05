package pers.meteor.common.util.captcha.core.client.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import pers.meteor.common.util.captcha.config.CaptchaProperties;
import pers.meteor.common.util.captcha.core.client.CaptchaClient;
import pers.meteor.common.util.captcha.core.client.CaptchaClientFactory;
import pers.meteor.common.util.captcha.core.enums.CaptchaTypeEnum;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author meteor
 */
@Slf4j
public class CaptchaClientFactoryImpl implements CaptchaClientFactory {
    /**
     * 验证码客户端
     */
    private final ConcurrentMap<String, CaptchaClient> captchaClients = new ConcurrentHashMap<>();

    /**
     * 构造函数
     * @param properties 属性配置
     * @param redisService 缓存服务
     */
    public CaptchaClientFactoryImpl(CaptchaProperties properties, RedisTemplate<String, String> redisService) {
        captchaClients.put(CaptchaTypeEnum.CIRCLE.name(), new CircleCaptchaClient(properties, redisService));
        captchaClients.put(CaptchaTypeEnum.LINE.name(), new LineCaptchaClient(properties, redisService));
        captchaClients.put(CaptchaTypeEnum.SHEAR.name(), new ShearCaptchaClient(properties, redisService));
        captchaClients.put(CaptchaTypeEnum.GIF.name(), new GifCaptchaClient(properties, redisService));
    }

    @Override
    public CaptchaClient getCaptchaClient(String captchaType) {
        return captchaClients.get(captchaType.toUpperCase());
    }

    @Override
    public void registerCaptchaClient(CaptchaClient captchaClient) {
        captchaClients.put(captchaClient.getCaptchaType().toUpperCase(), captchaClient);
    }
}
