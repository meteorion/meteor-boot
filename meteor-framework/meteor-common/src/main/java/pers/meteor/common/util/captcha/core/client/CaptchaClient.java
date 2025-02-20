package pers.meteor.common.util.captcha.core.client;

import pers.meteor.common.util.captcha.core.CaptchaRequestForm;
import pers.meteor.common.util.captcha.core.CaptchaResponse;
import pers.meteor.common.util.captcha.core.CaptchaValidateForm;

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
