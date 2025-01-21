package pers.meteor.auth.framework.captcha.core.client.impl;

import cn.hutool.captcha.AbstractCaptcha;
import cn.hutool.captcha.CaptchaUtil;
import pers.meteor.auth.framework.captcha.config.CaptchaProperties;
import pers.meteor.auth.framework.captcha.core.enums.CaptchaTypeEnum;
import pers.meteor.auth.model.captcha.form.CaptchaRequestForm;
import pers.meteor.common.redis.service.RedisService;

import java.awt.*;

/**
 * @author meteor
 */
public class ShearCaptchaClient extends AbstractCaptchaClient {
    private final AbstractCaptcha captcha;

    public ShearCaptchaClient(CaptchaProperties captchaProperties, RedisService redisService) {
        super(CaptchaTypeEnum.SHEAR.name(), captchaProperties, redisService);
        captcha = CaptchaUtil.createShearCaptcha(captchaProperties.getWidth(), captchaProperties.getHeight(),
                captchaProperties.getCode().getLength(), captchaProperties.getInterfereCount());
        captcha.setTextAlpha(captchaProperties.getTextAlpha());
        captcha.setGenerator(getCodeGenerator());
        captcha.setFont(getCaptchaFont());
    }

    @Override
    protected CaptchaModel createCaptcha(CaptchaRequestForm requestForm) {
        return CaptchaModel.builder()
                .code(captcha.getCode())
                .codeImageBase64(captcha.getImageBase64Data())
                .build();
    }
}
