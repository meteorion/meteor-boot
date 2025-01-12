package pers.meteor.common.security.core.service.impl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import pers.meteor.common.constant.SecurityConstants;
import pers.meteor.common.exception.ServiceException;
import pers.meteor.common.security.config.SecurityProperties;
import pers.meteor.common.security.core.model.AccessToken;
import pers.meteor.common.security.core.model.AuthUserDetail;
import pers.meteor.common.security.core.model.RefreshToken;
import pers.meteor.common.security.core.service.JwtService;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author meteor
 */
@Slf4j
public class JwtServiceImpl implements JwtService {

    private SecurityProperties.JwtProperties jwt;

    @Override
    public void setJwt(SecurityProperties.JwtProperties jwt) {
        this.jwt = jwt;
    }

    @Override
    public String createToken(Map<String, Object> claims) {
        return createToken(claims, 0);
    }

    @Override
    public String createToken(Map<String, Object> claims, int expireTime) {
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration((Date.from(LocalDateTime.now().plusMinutes(expireTime).atZone(ZoneId.systemDefault()).toInstant())))
                .signWith(SignatureAlgorithm.HS256, jwt.getSecret())
                .compact();
    }

    @Override
    public AccessToken createAccessToken(AuthUserDetail userDetail) {
        RefreshToken refreshToken = createRefreshToken(userDetail);

        HashMap<String, Object> claims = new HashMap<>();
        claims.put(SecurityConstants.DETAILS_USER_ID, userDetail.getId());
        claims.put(SecurityConstants.DETAILS_USER_TYPE, userDetail.getUserType());
        claims.put(SecurityConstants.DETAILS_CLIENT_ID, userDetail.getClientId());
        claims.put(SecurityConstants.DETAILS_USERNAME, userDetail.getUserName());
        claims.put(SecurityConstants.DETAILS_MOBILE, userDetail.getMobile());
        claims.put(SecurityConstants.DETAILS_ROLE, userDetail.getRoles());
        claims.put(SecurityConstants.DETAILS_REFREST_TOKEN, refreshToken.getRefreshToken());

        String accessToken = createToken(claims, jwt.getAccessTokenValiditySeconds());

        return AccessToken.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken.getRefreshToken())
                .userId(userDetail.getId())
                .userType(userDetail.getUserType())
                .userInfo(userDetail.getUserInfo())
                .clientId(userDetail.getClientId())
                .scopes(userDetail.getScopes())
                .expiresTime(LocalDateTime.now().plusSeconds(jwt.getAccessTokenValiditySeconds()))
                .build();
    }

    @Override
    public RefreshToken createRefreshToken(AuthUserDetail userDetail) {
        HashMap<String, Object> claims = new HashMap<>();
        claims.put(SecurityConstants.DETAILS_USER_ID, userDetail.getId());
        claims.put(SecurityConstants.DETAILS_USER_TYPE, userDetail.getUserType());
        claims.put(SecurityConstants.DETAILS_CLIENT_ID, userDetail.getClientId());

        String refreshToken = createToken(claims, jwt.getRefreshTokenValiditySeconds());

        return RefreshToken.builder()
                .refreshToken(refreshToken)
                .userId(userDetail.getId())
                .userType(userDetail.getUserType())
                .clientId(userDetail.getClientId())
                .scopes(userDetail.getScopes())
                .expiresTime(LocalDateTime.now().plusSeconds(jwt.getRefreshTokenValiditySeconds()))
                .build();
    }

    @Override
    public Claims parseToken(String token) {
        return Jwts.parser().setSigningKey(jwt.getSecret()).parseClaimsJws(token).getBody();
    }

    @Override
    public AccessToken parseAccessToken(String token) {
        try {
            Claims claims = parseToken(token);
            return AccessToken.builder()
                    .accessToken(token)
                    .refreshToken(claims.get(SecurityConstants.DETAILS_REFREST_TOKEN, String.class))
                    .userId(claims.get(SecurityConstants.DETAILS_USER_ID, Integer.class))
                    .userType(claims.get(SecurityConstants.DETAILS_USER_TYPE, Integer.class))
                    .clientId(claims.get(SecurityConstants.DETAILS_CLIENT_ID, String.class))
                    .expiresTime(convertToLocalDateTime(claims.getExpiration()))
                    .build();
        } catch (Exception e) {
            throw new ServiceException("无效访问令牌");
        }
    }

    @Override
    public RefreshToken parseRefreshToken(String token) {
        try {
            Claims claims = parseToken(token);
            return RefreshToken.builder()
                    .refreshToken(token)
                    .userId(claims.get(SecurityConstants.DETAILS_USER_ID, Integer.class))
                    .userType(claims.get(SecurityConstants.DETAILS_USER_TYPE, Integer.class))
                    .clientId(claims.get(SecurityConstants.DETAILS_CLIENT_ID, String.class))
                    .expiresTime(convertToLocalDateTime(claims.getExpiration()))
                    .build();
        } catch (Exception e) {
            throw new ServiceException("无效刷新令牌");
        }
    }

    @Override
    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(jwt.getSecret())
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            log.error("Invalid JWT token: {}", e.getMessage());
            return false;
        }
    }

    public static LocalDateTime convertToLocalDateTime(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }
}
