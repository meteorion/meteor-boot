package pers.meteor.auth.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pers.meteor.auth.api.dto.AccessToken;
import pers.meteor.auth.api.dto.AuthUserDetail;
import pers.meteor.auth.convert.AuthConvert;
import pers.meteor.auth.dto.form.*;
import pers.meteor.auth.dto.vo.AuthLoginVO;
import pers.meteor.auth.service.log.AuthLogService;
import pers.meteor.auth.service.token.TokenService;
import pers.meteor.auth.service.user.AuthUserService;
import pers.meteor.auth.service.user.AuthUserServiceFactory;
import pers.meteor.common.enums.LoginLogTypeEnum;
import pers.meteor.common.enums.LoginResultEnum;
import pers.meteor.common.exception.ServiceException;

import javax.annotation.Resource;

/**
 * @author meteor
 */
@Service
@Slf4j
public class AuthServiceImpl implements AuthService {

    @Resource
    private TokenService tokenService;
    @Resource
    private AuthUserServiceFactory authUserServiceFactory;
    @Resource
    private AuthLogService authLogService;

    @Override
    public AuthLoginVO login(AuthLoginForm loginForm) {
        AuthUserService userService = authUserServiceFactory.getUserService(loginForm.getUserType());
        // 校验手机密码并获取用户信息
        AuthUserDetail authUser = userService.getUserByAccount(loginForm.getAccount());
        if (authUser == null) {
            authLogService.saveLoginRecord(null, LoginLogTypeEnum.LOGIN_MOBILE, LoginResultEnum.BAD_CREDENTIALS);
            throw new ServiceException("登录失败，账号密码不正确");
        }
        // 校验密码
        if (!userService.isPasswordMatch(authUser, loginForm.getPassword())) {
            authLogService.saveLoginRecord(authUser, LoginLogTypeEnum.LOGIN_MOBILE, LoginResultEnum.BAD_CREDENTIALS);
            throw new ServiceException("登录失败，账号密码不正确");
        }
        // 校验账户是否锁定
        if (authUser.isAccountLocked()) {
            authLogService.saveLoginRecord(authUser, LoginLogTypeEnum.LOGIN_MOBILE, LoginResultEnum.USER_DISABLED);
            throw new ServiceException("登录失败，账号已被锁定，请联系管理员");
        }

        // 记录登录日志
        authLogService.saveLoginRecord(authUser, LoginLogTypeEnum.LOGIN_MOBILE, LoginResultEnum.SUCCESS);

        // 创建令牌
        AccessToken accessToken = tokenService.createAccessToken(authUser);

        return AuthConvert.INSTANCE.convertAuthLogin(accessToken, authUser.getOpenId());
    }

    @Override
    public void logout(String token) {
        AccessToken accessToken = tokenService.removeAccessToken(token);
        if (accessToken == null) {
            return;
        }
        authLogService.saveLogoutLog(accessToken.getUserId(), LoginLogTypeEnum.LOGIN_MOBILE);
    }

    @Override
    public AuthLoginVO smsLogin(AuthSmsLoginForm loginForm) {
        return null;
    }

    @Override
    public AuthLoginVO socialLogin(AuthSocialLoginForm loginForm) {
        return null;
    }

    @Override
    public AuthLoginVO weixinMiniAppLogin(AuthWechatMiniAppLoginForm loginForm) {
        return null;
    }

    @Override
    public String getSocialAuthorizeUrl(Integer type, String redirectUri) {
        return "";
    }

    @Override
    public void sendSmsCode(Long userId, AuthSmsSendForm smsSendForm) {

    }

    @Override
    public void validateSmsCode(Long userId, AuthSmsValidateForm smsValidateForm) {

    }

    @Override
    public AuthLoginVO refreshToken(String refreshToken) {
        return null;
    }
}
