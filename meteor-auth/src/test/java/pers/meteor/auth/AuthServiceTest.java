package pers.meteor.auth;

import com.alibaba.fastjson.JSON;
import org.junit.jupiter.api.Test;
import pers.meteor.common.security.config.SecurityProperties;
import pers.meteor.common.security.core.model.AccessToken;
import pers.meteor.common.security.core.model.AuthUserDetail;
import pers.meteor.common.security.core.model.RefreshToken;
import pers.meteor.common.security.core.service.JwtService;
import pers.meteor.common.security.core.service.impl.JwtServiceImpl;

import java.util.ArrayList;

/**
 * @author meteor
 */
public class AuthServiceTest {

    @Test
    public void testCreateToken() {
        JwtService jwtService = getJwtService();

        AuthUserDetail userDetail = new AuthUserDetail();
        userDetail.setId(1);
        userDetail.setUserName("meteor");
        userDetail.setUserType(1);
        userDetail.setClientId("meteor");
        ArrayList<String> scopes = new ArrayList<>();
        scopes.add("all");
        userDetail.setScopes(scopes);
        AccessToken accessToken = jwtService.createAccessToken(userDetail);
        System.out.println(accessToken);
    }

    @Test
    public void testParseToken() {
        JwtService jwtService = getJwtService();
        AccessToken accessToken = jwtService.parseAccessToken("eyJhbGciOiJIUzI1NiJ9.eyJyZWZyZXNoX3Rva2VuIjoiZXlKaGJHY2lPaUpJVXpJMU5pSjkuZXlKMWMyVnlYM1I1Y0dVaU9qRXNJblZ6WlhKZmFXUWlPakVzSW1WNGNDSTZNVGMzTWprNE1UazNOQ3dpYVdGMElqb3hOek0yTmprek9UYzBMQ0pqYkdsbGJuUmZhV1FpT2lKdFpYUmxiM0lpZlEuLW5sRDQxNXFxSkpqbWN5ekZSaUZOa3lWMXJiNjlvejQtRWxuTlZYSHFWNCIsInVzZXJfdHlwZSI6MSwicm9sZSI6bnVsbCwidXNlcl9pZCI6MSwibW9iaWxlIjpudWxsLCJleHAiOjE3NDE4Nzc5NzUsImlhdCI6MTczNjY5Mzk3NSwiY2xpZW50X2lkIjoibWV0ZW9yIiwidXNlcm5hbWUiOiJtZXRlb3IifQ.EoZUpCig0hmkVaz8y3D9n2I-zvsiesXVvzMpgREYcZQ");
        System.out.println(JSON.toJSONString(accessToken));
        RefreshToken refreshToken = jwtService.parseRefreshToken("eyJhbGciOiJIUzI1NiJ9.eyJ1c2VyX3R5cGUiOjEsInVzZXJfaWQiOjEsImV4cCI6MTc3Mjk4MTk3NCwiaWF0IjoxNzM2NjkzOTc0LCJjbGllbnRfaWQiOiJtZXRlb3IifQ.-nlD415qqJJjmcyzFRiFNkyV1rb69oz4-ElnNVXHqV4");
        System.out.println(JSON.toJSONString(refreshToken));

    }

    public JwtService getJwtService() {
        JwtServiceImpl jwtService = new JwtServiceImpl();
        SecurityProperties.JwtProperties jwtProperties = new SecurityProperties.JwtProperties();
        jwtProperties.setSecret("abcdefghijklmnopqrstuvwxyz");
        jwtProperties.setAccessTokenValiditySeconds(60 * 60 * 24);
        jwtProperties.setRefreshTokenValiditySeconds(60 * 60 * 24 * 7);
        jwtService.setJwt(jwtProperties);
        return jwtService;
    }
}
