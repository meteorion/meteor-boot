package pers.meteor.security.core.utils;

import javax.servlet.http.HttpServletRequest;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import org.springframework.lang.Nullable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import pers.meteor.auth.api.dto.AuthUserDetail;
import pers.meteor.common.constant.SecurityConstants;
import pers.meteor.common.utils.ServletUtils;
import pers.meteor.web.core.util.WebUtils;

import java.util.Collections;

/**
 * 权限获取工具类
 *
 * @author meteor
 */
public class SecurityUtils {

    /**
     * HEADER 认证头 value 的前缀
     */
    public static final String AUTHORIZATION_BEARER = "Bearer";

    public static final String LOGIN_USER_HEADER = "login-user";

    public static String getToken() {
        HttpServletRequest request = ServletUtils.getRequest();
        if (request != null) {
            return getToken(request);
        }
        return "";
    }

    public static String getToken(HttpServletRequest request) {
        return obtainAuthorization(request, SecurityConstants.AUTHORIZATION_HEADER, SecurityConstants.AUTHORIZATION_HEADER);
    }

    /**
     * 从请求中，获得认证 Token
     *
     * @param request 请求
     * @param headerName 认证 Token 对应的 Header 名字
     * @param parameterName 认证 Token 对应的 Parameter 名字
     * @return 认证 Token
     */
    public static String obtainAuthorization(HttpServletRequest request,
                                             String headerName, String parameterName) {
        // 1. 获得 Token。优先级：Header > Parameter
        String token = request.getHeader(headerName);
        if (StrUtil.isEmpty(token)) {
            token = request.getParameter(parameterName);
        }
        if (!org.springframework.util.StringUtils.hasText(token)) {
            return null;
        }
        // 2. 去除 Token 中带的 Bearer
        int index = token.indexOf(AUTHORIZATION_BEARER + " ");
        return index >= 0 ? token.substring(index + 7).trim() : token;
    }

    /**
     * 获得当前认证信息
     *
     * @return 认证信息
     */
    public static Authentication getAuthentication() {
        SecurityContext context = SecurityContextHolder.getContext();
        if (context == null) {
            return null;
        }
        return context.getAuthentication();
    }

    /**
     * 获取当前用户
     *
     * @return 当前用户
     */
    @Nullable
    public static AuthUserDetail getAuthUser() {
        Authentication authentication = getAuthentication();
        if (authentication == null) {
            return null;
        }
        return authentication.getPrincipal() instanceof AuthUserDetail ? (AuthUserDetail) authentication.getPrincipal() : null;
    }

    /**
     * 获得当前用户的编号，从上下文中
     *
     * @return 用户编号
     */
    @Nullable
    public static Integer getLoginUserId() {
        AuthUserDetail loginUser = getAuthUser();
        return loginUser != null ? loginUser.getId() : null;
    }

    /**
     * 获得当前用户的昵称，从上下文中
     *
     * @return 昵称
     */
    @Nullable
    public static String getLoginUserNickName() {
        AuthUserDetail loginUser = getAuthUser();
        return loginUser != null ? MapUtil.getStr(loginUser.getUserInfo(), SecurityConstants.DETAILS_USERNAME) : null;
    }

    /**
     * 获得当前用户的部门编号，从上下文中
     *
     * @return 部门编号
     */
    @Nullable
    public static Long getLoginUserDeptId() {
        AuthUserDetail loginUser = getAuthUser();
        return loginUser != null ? MapUtil.getLong(loginUser.getUserInfo(), SecurityConstants.DETAILS_USER_DEPT_ID) : null;
    }

    /**
     * 设置当前用户
     *
     * @param authUser 登录用户
     * @param request 请求
     */
    public static void setLoginUser(AuthUserDetail authUser, HttpServletRequest request) {
        // 创建 Authentication，并设置到上下文
        Authentication authentication = buildAuthentication(authUser, request);
        org.springframework.security.core.context.SecurityContextHolder.getContext().setAuthentication(authentication);

        // 额外设置到 request 中，用于 ApiAccessLogFilter 可以获取到用户编号；
        // 原因是，Spring Security 的 Filter 在 ApiAccessLogFilter 后面，在它记录访问日志时，线上上下文已经没有用户编号等信息
        WebUtils.setLoginUserId(request, authUser.getId());
        WebUtils.setLoginUserType(request, authUser.getUserType());
    }

    private static Authentication buildAuthentication(AuthUserDetail authUser, HttpServletRequest request) {
        // 创建 UsernamePasswordAuthenticationToken 对象
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                authUser, null, Collections.emptyList());
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        return authenticationToken;
    }
}
