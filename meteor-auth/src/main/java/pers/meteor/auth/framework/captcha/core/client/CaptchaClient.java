package pers.meteor.auth.framework.captcha.core.client;

import pers.meteor.auth.model.captcha.form.CaptchaRequestForm;
import pers.meteor.auth.model.captcha.vo.CaptchaResponse;

/**
 * @author meteor
 */
public interface CaptchaClient {
    /**
     * 获取验证码类型
     *
     * @return 验证码类型
     */
    String getCaptchaType();

    /**
     * 生成验证码
     *
     * @param requestForm 请求对象
     * @return 验证码响应对象
     */
    CaptchaResponse generate(CaptchaRequestForm requestForm);
}
