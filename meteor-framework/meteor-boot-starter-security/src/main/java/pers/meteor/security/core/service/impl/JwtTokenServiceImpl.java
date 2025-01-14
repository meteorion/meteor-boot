package pers.meteor.security.core.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import pers.meteor.common.exception.ServiceException;
import pers.meteor.security.config.SecurityProperties;
import pers.meteor.security.core.model.AccessToken;
import pers.meteor.security.core.model.AuthUserDetail;
import pers.meteor.security.core.model.RefreshToken;
import pers.meteor.security.core.service.AbstractTokenService;
import pers.meteor.security.core.service.JwtService;
import pers.meteor.security.core.utils.SecurityUtils;

import java.util.Objects;

/**
 * @author meteor
 */
@Slf4j
@RequiredArgsConstructor
public class JwtTokenServiceImpl extends AbstractTokenService {

    private final SecurityProperties.JwtProperties jwt;
    private final JwtService jwtService;

    @Override
    public AccessToken createAccessToken(AuthUserDetail userDetail) {
        AccessToken accessToken = jwtService.createAccessToken(userDetail);
        tokenStorgeService.saveAccessToken(accessToken, jwt.getAccessTokenValiditySeconds());
        return accessToken;
    }

    @Override
    public AccessToken refreshAccessToken(String token, String clientId) {
        // 解析token
        AccessToken accessToken = jwtService.parseAccessToken(SecurityUtils.getToken());
        if (!Objects.equals(accessToken.getRefreshToken(), token)) {
            throw new ServiceException("无效刷新令牌");
        }
        // 解析刷新令牌
        RefreshToken refreshToken = jwtService.parseRefreshToken(token);
        if (refreshToken.isExpired()) {
            throw new ServiceException("刷新令牌已过期");
        }

        // 生成新访问令牌
        AuthUserDetail userDetail = SecurityUtils.getUserDetail();
        accessToken.setScopes(refreshToken.getScopes());
        accessToken = jwtService.createAccessToken(userDetail);
        accessToken.setRefreshToken(refreshToken.getRefreshToken());

        return accessToken;
    }

    @Override
    public AccessToken checkAccessToken(String token) {
        // 判断是否缓存失效
        if (tokenStorgeService.getAccessToken(token) == null) {
            throw new ServiceException("访问令牌已失效");
        }
        AccessToken accessToken = jwtService.parseAccessToken(token);
        if (accessToken.isExpired()) {
            throw new ServiceException("访问令牌已过期");
        }
        return accessToken;
    }
}
