package pers.meteor.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pers.meteor.auth.framework.captcha.config.CaptchaProperties;
import pers.meteor.auth.framework.captcha.core.client.CaptchaClient;
import pers.meteor.auth.framework.captcha.core.client.CaptchaClientFactory;
import pers.meteor.auth.model.captcha.form.CaptchaRequestForm;
import pers.meteor.auth.model.captcha.form.CaptchaSmsSendForm;
import pers.meteor.auth.model.captcha.form.CaptchaSmsValidateForm;
import pers.meteor.auth.model.captcha.form.CaptchaValidateForm;
import pers.meteor.auth.model.captcha.vo.CaptchaResponse;

/**
 * @author meteor
 */
@Service
@RequiredArgsConstructor
public class CaptchaServiceImpl implements CaptchaService {
    private final CaptchaProperties captchaProperties;
    private final CaptchaClientFactory captchaClientFactory;

    @Override
    public CaptchaResponse getCaptcha(CaptchaRequestForm requestForm) {
        CaptchaClient captchaClient = captchaClientFactory.getCaptchaClient(captchaProperties.getType());
        return captchaClient.generate(requestForm);
    }

    @Override
    public CaptchaResponse validateCaptcha(CaptchaValidateForm validateForm) {
        return null;
    }

    @Override
    public void sendSmsCode(Long userId, CaptchaSmsSendForm smsSendForm) {

    }

    @Override
    public void validateSmsCode(Long userId, CaptchaSmsValidateForm smsValidateForm) {

    }
}
