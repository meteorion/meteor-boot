package pers.meteor.auth.service;

import pers.meteor.auth.model.auth.form.AuthLoginForm;
import pers.meteor.auth.model.auth.form.AuthSmsLoginForm;
import pers.meteor.auth.model.auth.form.AuthSocialLoginForm;
import pers.meteor.auth.model.auth.form.AuthWechatMiniAppLoginForm;
import pers.meteor.auth.model.auth.vo.AuthLoginVO;

import javax.validation.Valid;

/**
 * @author meteor
 */
public interface AuthService {

    /**
     * 手机 + 密码登录
     *
     * @param loginForm 登录信息
     * @return 登录结果
     */
    AuthLoginVO login(@Valid AuthLoginForm loginForm);

    /**
     * 基于 token 退出登录
     *
     * @param token token
     */
    void logout(String token);

    /**
     * 手机 + 验证码登陆
     *
     * @param loginForm    登陆信息
     * @return 登录结果
     */
    AuthLoginVO smsLogin(@Valid AuthSmsLoginForm loginForm);

    /**
     * 社交登录，使用 code 授权码
     *
     * @param loginForm 登录信息
     * @return 登录结果
     */
    AuthLoginVO socialLogin(@Valid AuthSocialLoginForm loginForm);

    /**
     * 微信小程序的一键登录
     *
     * @param loginForm 登录信息
     * @return 登录结果
     */
    AuthLoginVO weixinMiniAppLogin(AuthWechatMiniAppLoginForm loginForm);

    /**
     * 获得社交认证 URL
     *
     * @param type 社交平台类型
     * @param redirectUri 跳转地址
     * @return 认证 URL
     */
    String getSocialAuthorizeUrl(Integer type, String redirectUri);

    /**
     * 刷新访问令牌
     *
     * @param refreshToken 刷新令牌
     * @return 登录结果
     */
    AuthLoginVO refreshToken(String refreshToken);

}
