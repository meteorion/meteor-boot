package pers.meteor.auth.framework.captcha.core.client;

import org.checkerframework.checker.units.qual.C;
import pers.meteor.auth.model.captcha.form.CaptchaRequestForm;
import pers.meteor.auth.model.captcha.form.CaptchaValidateForm;
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

    /**
     * 校验验证码
     *
     * @param validateForm 校验对象
     * @return 校验结果
     */
    boolean verification(CaptchaValidateForm validateForm);
}
