package pers.meteor.auth.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pers.meteor.auth.convert.AuthConvert;
import pers.meteor.auth.dto.form.*;
import pers.meteor.auth.dto.vo.AuthLoginVO;
import pers.meteor.common.exception.ServiceException;
import pers.meteor.common.security.core.model.AccessToken;
import pers.meteor.common.security.core.model.AuthUserDetail;
import pers.meteor.common.security.core.service.TokenService;

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
    private AuthUserService authUserService;

    @Override
    public AuthLoginVO login(AuthLoginForm loginForm) {
        // 校验手机密码并获取用户信息
        AuthUserDetail authUser = authUserService.getUserByMobile(loginForm.getClientId(), loginForm.getMobile());
        if (authUser == null) {
            throw new ServiceException("登录失败，账号密码不正确");
        }
        // 校验密码
        if (!authUserService.isPasswordMatch(authUser, loginForm.getPassword())) {
            throw new ServiceException("登录失败，账号密码不正确");
        }
        // 校验账户是否锁定
        if (authUser.isAccountLocked()) {
            throw new ServiceException("登录失败，账号已被锁定，请联系管理员");
        }
        // todo 保存登录日志

        // 创建令牌
        AccessToken accessToken = tokenService.createAccessToken(authUser);

        return AuthConvert.INSTANCE.convert(accessToken, authUser.getOpenId());
    }

    @Override
    public void logout(String token) {

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
