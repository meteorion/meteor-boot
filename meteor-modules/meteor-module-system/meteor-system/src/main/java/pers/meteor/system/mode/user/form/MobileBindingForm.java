package pers.meteor.system.mode.user.form;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 绑定手机表单
 *
 * @author Ray
 * @since 2024/8/19
 */
@Schema(description = "绑定手机表单")
@Data
public class MobileBindingForm {

    @Schema(description = "手机号码")
    private String mobile;

    @Schema(description = "验证码")
    private String code;

}
