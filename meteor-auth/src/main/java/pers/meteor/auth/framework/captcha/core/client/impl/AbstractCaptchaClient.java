package pers.meteor.auth.framework.captcha.core.client.impl;

import cn.hutool.core.util.IdUtil;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import pers.meteor.auth.framework.captcha.config.CaptchaProperties;
import pers.meteor.auth.framework.captcha.core.client.CaptchaClient;
import pers.meteor.auth.model.captcha.form.CaptchaRequestForm;
import pers.meteor.auth.model.captcha.vo.CaptchaResponse;
import pers.meteor.common.redis.service.RedisService;

import java.util.concurrent.TimeUnit;

/**
 * @author meteor
 */
@RequiredArgsConstructor
public abstract class AbstractCaptchaClient implements CaptchaClient {
    protected static final String CAPTCHA_CODE_PREFIX = "captcha_code:";

    protected final String captchaType;
    protected final CaptchaProperties captchaProperties;
    protected final RedisService redisService;

    @Override
    public String getCaptchaType() {
        return captchaType;
    }

    @Override
    public CaptchaResponse generate(CaptchaRequestForm requestForm) {
        CaptchaModel captchaModel = createCaptcha(requestForm);

        String captchaKey = getCaptchaKey(requestForm);
        saveCaptcha(captchaKey, captchaModel);

        return CaptchaResponse.builder()
                .captchaKey(captchaKey)
                .captchaBase64(captchaModel.getCodeImageBase64())
                .build();
    }

    protected abstract CaptchaModel createCaptcha(CaptchaRequestForm requestForm);

    protected String getCaptchaKey(CaptchaRequestForm requestForm) {
        return IdUtil.fastSimpleUUID();
    }

    protected void saveCaptcha(String captchaKey, CaptchaModel captchaModel) {
        redisService.setCacheObject( CAPTCHA_CODE_PREFIX + captchaKey, captchaModel.getCode(), captchaProperties.getExpireSeconds(), TimeUnit.SECONDS);
    }

    @Data
    @Builder
    public static class CaptchaModel {
        /**
         * 验证码
         */
        private String code;

        /**
         * 验证码图片
         */
        private String codeImageBase64;
    }
}
