package pers.meteor.auth.api.oauth2.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * @author 钟宗兵
 * @since 1.0.0
 */
@Schema(description = "OAuth2 访问令牌O")
@Data
public class OAuthToken implements Serializable {
    private String id;

    @Schema(description = "用户编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "10")
    private Long userId;

    @Schema(description = "用户类型，参见 UserTypeEnum 枚举", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private String clientId;

    private String username;

    private String accessToken;

    private String issuedAt;

    private String expiresAt;
}
