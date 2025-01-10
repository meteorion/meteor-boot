package pers.meteor.auth.controller;

import cn.hutool.core.util.StrUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pers.meteor.auth.dto.form.*;
import pers.meteor.auth.dto.vo.AuthLoginVO;
import pers.meteor.auth.service.AuthService;
import pers.meteor.common.pojo.response.SingleResponse;
import pers.meteor.common.security.utils.SecurityUtils;

import javax.annotation.Resource;
import javax.annotation.security.PermitAll;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * @author meteor
 */
@Tag(name = "鉴权认证")
@RestController
@RequestMapping("/auth")
@Validated
@Slf4j
public class AuthController {

    @Resource
    private AuthService authService;

    @PostMapping("/login")
    @Operation(summary = "使用手机 + 密码登录")
    @PermitAll
    public SingleResponse<AuthLoginVO> login(@RequestBody @Valid AuthLoginForm loginForm) {
        return SingleResponse.success(authService.login(loginForm));
    }

    @PostMapping("/logout")
    @Operation(summary = "登出系统")
    @PermitAll
    public SingleResponse<Boolean> logout(HttpServletRequest request) {
        String token = SecurityUtils.getToken(request);
        if (StrUtil.isNotBlank(token)) {
            authService.logout(token);
        }
        return SingleResponse.success(Boolean.TRUE);
    }

    @PostMapping("/refresh-token")
    @Operation(summary = "刷新令牌")
    @Parameter(name = "refreshToken", description = "刷新令牌", required = true)
    @PermitAll
    public SingleResponse<AuthLoginVO> refreshToken(@RequestParam("refreshToken") String refreshToken) {
        return SingleResponse.success(authService.refreshToken(refreshToken));
    }

    // ========== 短信登录相关 ==========

    @PostMapping("/sms-login")
    @Operation(summary = "使用手机 + 验证码登录")
    @PermitAll
    public SingleResponse<AuthLoginVO> smsLogin(@RequestBody @Valid AuthSmsLoginForm loginForm) {
        return SingleResponse.success(authService.smsLogin(loginForm));
    }

    @PostMapping("/send-sms-code")
    @Operation(summary = "发送手机验证码")
    @PermitAll
    public SingleResponse<Boolean> sendSmsCode(@RequestBody @Valid AuthSmsSendForm smsSendForm) {
        Long userId = SecurityUtils.getUserId();
        authService.sendSmsCode(userId, smsSendForm);
        return SingleResponse.success(true);
    }

    @PostMapping("/validate-sms-code")
    @Operation(summary = "校验手机验证码")
    @PermitAll
    public SingleResponse<Boolean> validateSmsCode(@RequestBody @Valid AuthSmsValidateForm smsValidateForm) {
        Long userId = SecurityUtils.getUserId();
        authService.validateSmsCode(userId, smsValidateForm);
        return SingleResponse.success(true);
    }

    // ========== 社交登录相关 ==========

    @GetMapping("/social-auth-redirect")
    @Operation(summary = "社交授权的跳转")
    @Parameters({
            @Parameter(name = "type", description = "社交类型", required = true),
            @Parameter(name = "redirectUri", description = "回调路径")
    })
    @PermitAll
    public SingleResponse<String> socialAuthRedirect(@RequestParam("type") Integer type,
                                                   @RequestParam("redirectUri") String redirectUri) {
        return SingleResponse.success(authService.getSocialAuthorizeUrl(type, redirectUri));
    }

    @PostMapping("/social-login")
    @Operation(summary = "社交快捷登录，使用 code 授权码", description = "适合未登录的用户，但是社交账号已绑定用户")
    @PermitAll
    public SingleResponse<AuthLoginVO> socialLogin(@RequestBody @Valid AuthSocialLoginForm loginForm) {
        return SingleResponse.success(authService.socialLogin(loginForm));
    }

    @PostMapping("/weixin-mini-app-login")
    @Operation(summary = "微信小程序的一键登录")
    @PermitAll
    public SingleResponse<AuthLoginVO> weixinMiniAppLogin(@RequestBody @Valid AuthWechatMiniAppLoginForm loginForm) {
        return SingleResponse.success(authService.weixinMiniAppLogin(loginForm));
    }
}
