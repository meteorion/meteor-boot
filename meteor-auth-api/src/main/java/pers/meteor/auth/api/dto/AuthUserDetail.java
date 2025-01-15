package pers.meteor.auth.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * @author meteor
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthUserDetail {
    /**
     * 用户id
     */
    private Integer id;
    /**
     * 手机号
     */
    private String mobile;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 角色
     */
    private List<String> roles;
    /**
     * 密码
     */
    private String password;
    /**
     * openId
     */
    private String openId;
    /**
     * 是否未锁定
     */
    private boolean accountLocked;
    /**
     * 用户类型
     */
    private Integer userType;
    /**
     * 用户信息
     */
    private Map<String, String> userInfo;
    /**
     * 客户端编号
     */
    private String clientId;
    /**
     * 授权范围，英文逗号分隔
     */
    private List<String> scopes;
    /**
     * 过期时间
     */
    private LocalDateTime expiresTime;
}
