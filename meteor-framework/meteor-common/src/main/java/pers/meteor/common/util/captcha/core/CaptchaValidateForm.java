package pers.meteor.common.util.captcha.core;

import lombok.Builder;
import lombok.Data;

/**
 *
 * @author meteor
 */
@Data
@Builder
public class CaptchaValidateForm {
    /**
     * 验证码ID
     */
    private String captchaKey;

    /**
     * 验证码
     */
    private String captchaCode;
}
