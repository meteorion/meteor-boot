package pers.meteor.common.security.core.service;

import pers.meteor.common.security.core.model.AccessToken;
import pers.meteor.common.security.core.model.RefreshToken;

/**
 * token仓储服务
 * @author meteor
 */
public interface TokenStorgeService {

    /**
     * 保存token
     *
     * @param accessToken token
     * @param expires 过期时间
     */
    void saveAccessToken(AccessToken accessToken, long expires);

    /**
     * 保存refreshToken
     *
     * @param refreshToken token
     * @param expires 过期时间
     */
    void saveRefreshToken(RefreshToken refreshToken, long expires);

    /**
     * 移除token
     *
     * @param token token
     * @return token
     */
    AccessToken removeAccessToken(String token);

    /**
     * 移除refreshToken
     *
     * @param token token
     * @return token
     */
    RefreshToken removeRefreshToken(String token);

    /**
     * 获取token
     *
     * @param token token
     * @return token
     */
    AccessToken getAccessToken(String token);

    /**
     * 获取refreshToken
     *
     * @param refreshToken /
     * @return /
     */
    RefreshToken getRefreshToken(String refreshToken);
}
