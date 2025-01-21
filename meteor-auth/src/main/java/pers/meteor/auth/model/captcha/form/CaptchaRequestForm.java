package pers.meteor.auth.model.captcha.form;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

/**
 * @author meteor
 */
@Schema(description = "验证码请求对象")
@Data
@Builder
public class CaptchaRequestForm {
    @Schema(description = "验证码类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private String captchaType;
}
