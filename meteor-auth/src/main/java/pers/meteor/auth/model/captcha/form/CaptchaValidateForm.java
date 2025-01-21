package pers.meteor.auth.model.captcha.form;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

/**
 *
 * @author meteor
 */
@Schema(description = "验证码校验对象")
@Data
@Builder
public class CaptchaValidateForm {
    /**
     * 验证码ID
     */
    private String captchaId;
}
