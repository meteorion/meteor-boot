package pers.meteor.auth.model;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @author meteor
 */
@Data
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
     * 授权范围
     */
    private List<String> scopes;
}
