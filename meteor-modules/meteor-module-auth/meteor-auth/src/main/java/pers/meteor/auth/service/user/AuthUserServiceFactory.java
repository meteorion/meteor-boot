package pers.meteor.auth.service.user;

/**
 * @author 钟宗兵
 * @since 1.0.0
 */
public interface AuthUserServiceFactory {

    /**
     * 注册用户服务
     *
     * @param userType      用户类型
     * @param authUserService 用户服务
     */
    void registerService(Integer userType, AuthUserService authUserService);

    /**
     * 根据手机号获取用户信息
     *
     * @param userType 用户类型
     * @return 用户信息
     */
    AuthUserService getUserService(Integer userType);
}
