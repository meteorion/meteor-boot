package pers.meteor.common.util.captcha.core.client.impl;

import cn.hutool.captcha.generator.CodeGenerator;
import cn.hutool.captcha.generator.MathGenerator;
import cn.hutool.captcha.generator.RandomGenerator;
import cn.hutool.core.util.IdUtil;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import pers.meteor.common.util.captcha.config.CaptchaProperties;
import pers.meteor.common.util.captcha.core.CaptchaRequestForm;
import pers.meteor.common.util.captcha.core.CaptchaResponse;
import pers.meteor.common.util.captcha.core.CaptchaValidateForm;
import pers.meteor.common.util.captcha.core.client.CaptchaClient;

import java.awt.*;
import java.util.concurrent.TimeUnit;

/**
 * @author meteor
 */
@RequiredArgsConstructor
public abstract class AbstractCaptchaClient implements CaptchaClient {
    protected static final String CAPTCHA_CODE_PREFIX = "captcha_code:";

    protected final String captchaType;
    protected final CaptchaProperties captchaProperties;
    protected final RedisTemplate<String, String> redisService;

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

    @Override
    public boolean verification(CaptchaValidateForm validateForm) {
        String captchaCode = getCaptchaCode(validateForm.getCaptchaKey());
        if (captchaCode == null) {
            return false;
        }
        return getCodeGenerator().verify(captchaCode, validateForm.getCaptchaCode());
    }

    protected abstract CaptchaModel createCaptcha(CaptchaRequestForm requestForm);

    protected String getCaptchaKey(CaptchaRequestForm requestForm) {
        return IdUtil.fastSimpleUUID();
    }

    protected void saveCaptcha(String captchaKey, CaptchaModel captchaModel) {
        redisService.opsForValue().set( CAPTCHA_CODE_PREFIX + captchaKey, captchaModel.getCode(), captchaProperties.getExpireSeconds(), TimeUnit.SECONDS);
    }

    protected String getCaptchaCode(String captchaKey) {
        return redisService.opsForValue().get(CAPTCHA_CODE_PREFIX + captchaKey);
    }

    protected CodeGenerator getCodeGenerator() {
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

    protected Font getCaptchaFont() {
        String fontName = captchaProperties.getFont().getName();
        int fontSize = captchaProperties.getFont().getSize();
        int fontWight = captchaProperties.getFont().getWeight();
        return new Font(fontName, fontWight, fontSize);
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
