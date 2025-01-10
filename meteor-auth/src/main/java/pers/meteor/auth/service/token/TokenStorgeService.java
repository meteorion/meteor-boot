package pers.meteor.auth.service.token;

import pers.meteor.auth.model.AccessToken;

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
     * 移除token
     *
     * @param token token
     * @return token
     */
    AccessToken removeAccessToken(String token);

    /**
     * 获取token
     *
     * @param token token
     * @return token
     */
    AccessToken getAccessToken(String token);
}
