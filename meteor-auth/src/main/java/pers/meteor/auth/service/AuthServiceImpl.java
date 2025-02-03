package pers.meteor.auth.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pers.meteor.auth.api.dto.AccessToken;
import pers.meteor.auth.api.dto.AuthUserDetail;
import pers.meteor.auth.convert.AuthConvert;
import pers.meteor.auth.model.auth.form.AuthLoginForm;
import pers.meteor.auth.model.auth.form.AuthSmsLoginForm;
import pers.meteor.auth.model.auth.form.AuthSocialLoginForm;
import pers.meteor.auth.model.auth.form.AuthWechatMiniAppLoginForm;
import pers.meteor.auth.model.auth.vo.AuthLoginVO;
import pers.meteor.auth.model.captcha.form.CaptchaValidateForm;
import pers.meteor.auth.service.log.AuthLogService;
import pers.meteor.auth.service.token.TokenService;
import pers.meteor.auth.service.user.AuthUserService;
import pers.meteor.auth.service.user.AuthUserServiceFactory;
import pers.meteor.common.enums.LoginLogTypeEnum;
import pers.meteor.common.enums.LoginResultEnum;
import pers.meteor.common.utils.validation.ValidationUtils;

import javax.annotation.Resource;
import javax.validation.Validator;

import static pers.meteor.common.exception.util.ServiceExceptionUtil.exception;
import static pers.meteor.system.enums.SystemErrorConstants.*;

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
    @Resource
    private CaptchaService captchaService;
    @Resource
    private Validator validator;

    @Override
    public AuthLoginVO login(AuthLoginForm loginForm) {
        if (captchaService.enabledCaptcha()) {
            ValidationUtils.validate(validator, loginForm, AuthLoginForm.CodeEnableGroup.class);
            boolean validateCaptcha = captchaService.validateCaptcha(CaptchaValidateForm.builder().captchaKey(loginForm.getCaptchaKey())
                    .captchaCode(loginForm.getCaptchaCode()).build());
            if (!validateCaptcha) {
                authLogService.saveLoginRecord(null, loginForm.getAccount(), LoginLogTypeEnum.LOGIN_MOBILE, LoginResultEnum.CAPTCHA_CODE_ERROR);
                throw exception(AUTH_LOGIN_CAPTCHA_CODE_ERROR);
            }
        }

        AuthUserService userService = authUserServiceFactory.getUserService(loginForm.getUserType());
        // 校验手机密码并获取用户信息
        AuthUserDetail authUser = userService.getUserByAccount(loginForm.getAccount());
        if (authUser == null) {
            authLogService.saveLoginRecord(null, loginForm.getAccount(), LoginLogTypeEnum.LOGIN_MOBILE, LoginResultEnum.BAD_CREDENTIALS);
            throw exception(AUTH_LOGIN_BAD_CREDENTIALS);
        }
        // 校验密码
        if (!userService.isPasswordMatch(authUser, loginForm.getPassword())) {
            authLogService.saveLoginRecord(authUser.getId(),loginForm.getAccount(), LoginLogTypeEnum.LOGIN_MOBILE, LoginResultEnum.BAD_CREDENTIALS);
            throw exception(AUTH_LOGIN_BAD_CREDENTIALS);
        }
        // 校验账户是否锁定
        if (authUser.isAccountLocked()) {
            authLogService.saveLoginRecord(authUser.getId(), loginForm.getAccount(), LoginLogTypeEnum.LOGIN_MOBILE, LoginResultEnum.USER_DISABLED);
            throw exception(AUTH_LOGIN_USER_DISABLED);
        }

        // 记录登录日志
        authLogService.saveLoginRecord(authUser.getId(), loginForm.getAccount(), LoginLogTypeEnum.LOGIN_MOBILE, LoginResultEnum.SUCCESS);

        // 创建令牌
        AccessToken accessToken = tokenService.createAccessToken(authUser);

        return AuthConvert.INSTANCE.convert(accessToken, authUser.getOpenId());
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
    public AuthLoginVO refreshToken(String refreshToken) {
        return null;
    }
}
