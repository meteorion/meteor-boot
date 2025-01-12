package pers.meteor.auth.service;

import pers.meteor.common.security.core.model.AuthUserDetail;

/**
 * @author meteor
 */
public interface AuthUserService {

    /**
     * 根据手机号查询用户信息
     *
     * @param appId 应用ID
     * @param mobile 手机号
     * @return 用户信息
     */
    AuthUserDetail getUserByMobile(String appId, String mobile);

    /**
     * 验证密码是否匹配
     *
     * @param authUser 用户
     * @param loginPassword 登录密码
     * @return 匹配结果
     */
    boolean isPasswordMatch(AuthUserDetail authUser, String loginPassword);
}
