package pers.meteor.common.util.captcha.core.client.impl;

import cn.hutool.captcha.AbstractCaptcha;
import cn.hutool.captcha.CaptchaUtil;
import org.springframework.data.redis.core.RedisTemplate;
import pers.meteor.common.util.captcha.config.CaptchaProperties;
import pers.meteor.common.util.captcha.core.CaptchaRequestForm;
import pers.meteor.common.util.captcha.core.enums.CaptchaTypeEnum;

/**
 * @author meteor
 */
public class CircleCaptchaClient extends AbstractCaptchaClient {
    private final AbstractCaptcha captcha;

    public CircleCaptchaClient(CaptchaProperties captchaProperties, RedisTemplate<String, String> redisService) {
        super(CaptchaTypeEnum.CIRCLE.name(), captchaProperties, redisService);
        captcha = CaptchaUtil.createCircleCaptcha(captchaProperties.getWidth(), captchaProperties.getHeight(),
                captchaProperties.getCode().getLength(), captchaProperties.getInterfereCount());
        captcha.setTextAlpha(captchaProperties.getTextAlpha());
        captcha.setGenerator(getCodeGenerator());
        captcha.setFont(getCaptchaFont());
    }

    @Override
    protected CaptchaModel createCaptcha(CaptchaRequestForm requestForm) {
        captcha.createCode();
        return CaptchaModel.builder()
                .code(captcha.getCode())
                .codeImageBase64(captcha.getImageBase64Data())
                .build();
    }
}
