package pers.meteor.auth.framework.captcha.core.client.impl;

import cn.hutool.captcha.AbstractCaptcha;
import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.generator.CodeGenerator;
import pers.meteor.auth.framework.captcha.config.CaptchaProperties;
import pers.meteor.auth.framework.captcha.core.enums.CaptchaTypeEnum;
import pers.meteor.auth.model.captcha.form.CaptchaRequestForm;
import pers.meteor.common.redis.service.RedisService;

import java.awt.*;

/**
 * @author meteor
 */
public class CircleCaptchaClient extends AbstractCaptchaClient {
    private final AbstractCaptcha captcha;

    public CircleCaptchaClient(CaptchaProperties captchaProperties, RedisService redisService, CodeGenerator codeGenerator, Font captchaFont) {
        super(CaptchaTypeEnum.CIRCLE.name(), captchaProperties, redisService);
        captcha = CaptchaUtil.createCircleCaptcha(captchaProperties.getWidth(), captchaProperties.getHeight(),
                captchaProperties.getCode().getLength(), captchaProperties.getInterfereCount());
        captcha.setTextAlpha(captchaProperties.getTextAlpha());
        captcha.setGenerator(codeGenerator);
        captcha.setFont(captchaFont);
    }

    @Override
    protected CaptchaModel createCaptcha(CaptchaRequestForm requestForm) {
        return CaptchaModel.builder()
                .code(captcha.getCode())
                .codeImageBase64(captcha.getImageBase64Data())
                .build();
    }
}
