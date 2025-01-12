package pers.meteor.common.security.core.service;

import io.jsonwebtoken.Claims;
import pers.meteor.common.security.config.SecurityProperties;
import pers.meteor.common.security.core.model.AccessToken;
import pers.meteor.common.security.core.model.AuthUserDetail;
import pers.meteor.common.security.core.model.RefreshToken;

import java.util.Map;

/**
 * @author meteor
 */
public interface JwtService {

    void setJwt(SecurityProperties.JwtProperties jwt);

    /**
     * 生成token
     * @param claims /
     * @return /
     */
    String createToken(Map<String, Object> claims);

    /**
     * 生成token
     *
     * @param claims /
     * @param expireTime /
     * @return /
     */
    String createToken(Map<String, Object> claims, int expireTime);

    /**
     * 生成token
     *
     * @param userDetail /
     * @return /
     */
    AccessToken createAccessToken(AuthUserDetail userDetail);

    /**
     * 生成token
     *
     * @param userDetail /
     * @return /
     */
    RefreshToken createRefreshToken(AuthUserDetail userDetail);

    /**
     * 解析token
     * @param token /
     * @return /
     */
    Claims parseToken(String token);

    /**
     * 解析token
     * @param token /
     * @return /
     */
    AccessToken parseAccessToken(String token);

    /**
     * 解析token
     * @param token /
     * @return /
     */
    RefreshToken parseRefreshToken(String token);

    /**
     * 验证token
     * @param token /
     * @return /
     */
    boolean validateToken(String token);
}
