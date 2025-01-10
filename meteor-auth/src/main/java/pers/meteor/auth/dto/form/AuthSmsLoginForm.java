package pers.meteor.auth.dto.form;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import pers.meteor.common.validation.Mobile;

import javax.validation.constraints.NotEmpty;

/**
 * @author 钟宗兵
 * @since 1.0.0
 */
@Schema(description = "用户 APP - 手机 + 密码登录 Request VO,如果登录并绑定社交用户，需要传递 social 开头的参数")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthSmsLoginForm {

 @Schema(description = "手机号", requiredMode = Schema.RequiredMode.REQUIRED, example = "15601691300")
 @NotEmpty(message = "手机号不能为空")
 @Mobile
 private String mobile;

 @Schema(description = "手机验证码", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
 @NotEmpty(message = "手机验证码不能为空")
 @Length(min = 4, max = 6, message = "手机验证码长度为 4-6 位")
 private String code;

 // ========== 绑定社交登录时，需要传递如下参数 ==========

 @Schema(description = "社交平台的类型，参见 SocialTypeEnum 枚举值", requiredMode = Schema.RequiredMode.REQUIRED, example = "10")
 private Integer socialType;

 @Schema(description = "授权码", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
 private String socialCode;

 @Schema(description = "state", requiredMode = Schema.RequiredMode.REQUIRED, example = "9b2ffbc1-7425-4155-9894-9d5c08541d62")
 private String socialState;
}
