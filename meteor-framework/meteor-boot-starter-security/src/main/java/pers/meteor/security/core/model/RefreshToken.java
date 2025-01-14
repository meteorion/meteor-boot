package pers.meteor.security.core.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pers.meteor.common.enums.UserTypeEnum;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author meteor
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RefreshToken implements Serializable {
    /**
     * 刷新令牌
     */
    private String refreshToken;
    /**
     * 用户编号
     */
    private Integer userId;
    /**
     * 用户类型
     * 枚举 {@link UserTypeEnum}
     */
    private Integer userType;
    /**
     * 客户端编号
     */
    private String clientId;
    /**
     * 授权范围
     */
    private List<String> scopes;
    /**
     * 过期时间
     */
    private LocalDateTime expiresTime;

    public boolean isExpired() {
        return expiresTime != null && LocalDateTime.now().isAfter(expiresTime);
    }
}
