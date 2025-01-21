package pers.meteor.auth.framework.captcha.core.client.impl;

import lombok.extern.slf4j.Slf4j;
import pers.meteor.auth.framework.captcha.config.CaptchaProperties;
import pers.meteor.auth.framework.captcha.core.client.CaptchaClient;
import pers.meteor.auth.framework.captcha.core.client.CaptchaClientFactory;
import pers.meteor.auth.framework.captcha.core.enums.CaptchaTypeEnum;
import pers.meteor.common.redis.service.RedisService;

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
    public CaptchaClientFactoryImpl(CaptchaProperties properties, RedisService redisService) {
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
