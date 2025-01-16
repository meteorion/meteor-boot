package pers.meteor.auth.dto.form;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author 钟宗兵
 * @since 1.0.0
 */
@Schema(description = "用户 APP - 手机 + 密码登录 Request VO,如果登录并绑定社交用户，需要传递 social 开头的参数")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthLoginForm {

 @Schema(description = "用户类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
 @NotNull(message = "用户类型不能为空")
 private Integer userType;

 @Schema(description = "客户端编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "10")
 @NotBlank(message = "客户端编号不能为空")
 private String clientId;

 @Schema(description = "账户名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "15601691300")
 @NotBlank(message = "账户名称不能为空")
 private String account;

 @Schema(description = "密码", requiredMode = Schema.RequiredMode.REQUIRED, example = "buzhidao")
 @NotBlank(message = "密码不能为空")
 @Length(min = 4, max = 16, message = "密码长度为 4-16 位")
 private String password;

 // ========== 绑定社交登录时，需要传递如下参数 ==========

 @Schema(description = "社交平台的类型，参见 SocialTypeEnum 枚举值", requiredMode = Schema.RequiredMode.REQUIRED, example = "10")
 private Integer socialType;

 @Schema(description = "授权码", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
 private String socialCode;

 @Schema(description = "state", requiredMode = Schema.RequiredMode.REQUIRED, example = "9b2ffbc1-7425-4155-9894-9d5c08541d62")
 private String socialState;
}
