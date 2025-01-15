package pers.meteor.auth.service.token;

import pers.meteor.auth.api.dto.AccessToken;
import pers.meteor.auth.api.dto.AccessTokenCheckResult;
import pers.meteor.auth.api.dto.AuthUserDetail;

/**
 * @author meteor
 */
public interface TokenService {

    /**
     * 创建访问令牌
     * 注意：该流程中，会包含创建刷新令牌的创建
     * 参考 DefaultTokenServices 的 createAccessToken 方法
     *
     * @param userDetail 信息
     * @return 访问令牌的信息
     */
    AccessToken createAccessToken(AuthUserDetail userDetail);

    /**
     * 刷新访问令牌
     * 参考 DefaultTokenServices 的 refreshAccessToken 方法
     *
     * @param token 刷新令牌
     * @param clientId 客户端编号
     * @return 访问令牌的信息
     */
    AccessToken refreshAccessToken(String token, String clientId);

    /**
     * 获得访问令牌
     * 参考 DefaultTokenServices 的 getAccessToken 方法
     *
     * @param accessToken 访问令牌
     * @return 访问令牌的信息
     */
    AccessToken getAccessToken(String accessToken);

    /**
     * 校验访问令牌
     *
     * @param accessToken 访问令牌
     * @return 访问令牌的信息
     */
    AccessTokenCheckResult checkAccessToken(String accessToken);

    /**
     * 移除访问令牌
     * 注意：该流程中，会移除相关的刷新令牌
     * 参考 DefaultTokenServices 的 revokeToken 方法
     *
     * @param accessToken 刷新令牌
     * @return 访问令牌的信息
     */
    AccessToken removeAccessToken(String accessToken);
}
