package pers.meteor.auth.model.captcha.form;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;
import pers.meteor.common.validation.Mobile;

import javax.validation.constraints.NotNull;

/**
 * @author 钟宗兵
 * @since 1.0.0
 */
@Schema(description = "用户 APP - 发送手机验证码 Request VO")
@Data
@Accessors(chain = true)
public class CaptchaSmsSendForm {
    @Schema(description = "手机号", example = "15601691234")
    @Mobile
    private String mobile;

    @Schema(description = "发送场景,对应 SmsSceneEnum 枚举", example = "1")
    @NotNull(message = "发送场景不能为空")
    private Integer scene;
}
