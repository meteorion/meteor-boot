package pers.meteor.auth.service;

import pers.meteor.auth.model.captcha.form.CaptchaRequestForm;
import pers.meteor.auth.model.captcha.form.CaptchaSmsSendForm;
import pers.meteor.auth.model.captcha.form.CaptchaSmsValidateForm;
import pers.meteor.auth.model.captcha.form.CaptchaValidateForm;
import pers.meteor.auth.model.captcha.vo.CaptchaResponse;

/**
 * @author meteor
 */
public interface CaptchaService {

    /**
     * 是否开启验证码
     *
     * @return /
     */
    boolean enabledCaptcha();

    /**
     * 获取验证码
     * @param requestForm /
     * @return /
     */
    CaptchaResponse getCaptcha(CaptchaRequestForm requestForm);

    /**
     * 校验验证码
     * @param validateForm /
     */
    boolean validateCaptcha(CaptchaValidateForm validateForm);

    /**
     * 给用户发送短信验证码
     *
     * @param userId 用户编号
     * @param smsSendForm 发送信息
     */
    void sendSmsCode(Long userId, CaptchaSmsSendForm smsSendForm);

    /**
     * 校验短信验证码是否正确
     *
     * @param userId 用户编号
     * @param smsValidateForm 校验信息
     */
    void validateSmsCode(Long userId, CaptchaSmsValidateForm smsValidateForm);
}
