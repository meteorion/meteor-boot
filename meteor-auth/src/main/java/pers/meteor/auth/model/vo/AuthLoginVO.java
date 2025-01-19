package pers.meteor.auth.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author 钟宗兵
 * @since 1.0.0
 */
@Schema(description = "用户 APP - 登录 Response VO")
@Data
public class AuthLoginVO {

    @Schema(description = "用户编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private Integer userId;

    @Schema(description = "访问令牌", requiredMode = Schema.RequiredMode.REQUIRED, example = "happy")
    private String accessToken;

    @Schema(description = "刷新令牌", requiredMode = Schema.RequiredMode.REQUIRED, example = "nice")
    private String refreshToken;

    @Schema(description = "过期时间", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime expiresTime;

    /**
     * 仅社交登录、社交绑定时会返回
     */
    @Schema(description = "社交用户 openid", example = "qq768")
    private String openId;
}
