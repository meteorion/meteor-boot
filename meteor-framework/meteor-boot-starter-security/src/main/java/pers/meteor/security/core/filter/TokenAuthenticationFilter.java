package pers.meteor.security.core.filter;

import cn.hutool.core.util.StrUtil;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.OncePerRequestFilter;
import pers.meteor.auth.api.AuthTokenApi;
import pers.meteor.auth.api.dto.AccessToken;
import pers.meteor.auth.api.dto.AuthUserDetail;
import pers.meteor.common.exception.ServiceException;
import pers.meteor.common.pojo.response.SingleResponse;
import pers.meteor.common.utils.ServletUtils;
import pers.meteor.common.utils.json.JsonUtils;
import pers.meteor.security.config.SecurityProperties;
import pers.meteor.security.core.utils.SecurityUtils;
import pers.meteor.web.core.handler.GlobalExceptionHandler;
import pers.meteor.web.core.util.WebUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

/**
 * Token 过滤器，验证 token 的有效性
 * 验证通过后，获得 {@link AuthUserDetail} 信息，并加入到 Spring Security 上下文
 *
 * @author meteor
 */
@RequiredArgsConstructor
@Slf4j
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    private final SecurityProperties securityProperties;

    private final GlobalExceptionHandler globalExceptionHandler;

    private final AuthTokenApi authTokenApi;

    @Override
    @SuppressWarnings("NullableProblems")
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        // 情况一，基于 header[login-user] 获得用户，例如说来自 Gateway 或者其它服务透传
        AuthUserDetail authUser = buildLoginUserByHeader(request);

        // 情况二，基于 Token 获得用户
        // 注意，这里主要满足直接使用 Nginx 直接转发到 Spring Cloud 服务的场景。
        if (authUser == null) {
            String token = SecurityUtils.obtainAuthorization(request,
                    securityProperties.getTokenHeader(), securityProperties.getTokenParameter());
            if (StrUtil.isNotEmpty(token)) {
                Integer userType = WebUtils.getLoginUserType(request);
                try {
                    // 1.1 基于 token 构建登录用户
                    authUser = buildLoginUserByToken(token);
                    // 1.2 模拟 Login 功能，方便日常开发调试
                    if (authUser == null) {
                        authUser = mockAuthUser(request, token, userType);
                    }
                } catch (Throwable ex) {
                    SingleResponse<?> result = globalExceptionHandler.allExceptionHandler(request, ex);
                    ServletUtils.writeJSON(response, result);
                    return;
                }
            }
        }

        // 设置当前用户
        if (authUser != null) {
            SecurityUtils.setLoginUser(authUser, request);
        }
        // 继续过滤链
        chain.doFilter(request, response);
    }

    private AuthUserDetail buildLoginUserByToken(String token) {
        try {
            AccessToken accessToken = authTokenApi.checkAccessToken(token).getCheckedData();
            AuthUserDetail authUser = new AuthUserDetail();
            authUser.setId(accessToken.getUserId());
            authUser.setUserType(accessToken.getUserType());
            authUser.setClientId(accessToken.getClientId());
            authUser.setUserInfo(accessToken.getUserInfo());
            authUser.setExpiresTime(accessToken.getExpiresTime());
            return authUser;
        } catch (ServiceException ex) {
            // 刷新 token 过期了，返回 null 即可
            return null;
        }
    }

    /**
     * 模拟登录用户，方便日常开发调试
     *
     * 注意，在线上环境下，一定要关闭该功能！！！
     *
     * @param request 请求
     * @param token 模拟的 token，格式为 {@link SecurityProperties#getMockSecret()} + 用户编号
     * @param userType 用户类型
     * @return 模拟的 LoginUser
     */
    private AuthUserDetail mockAuthUser(HttpServletRequest request, String token, Integer userType) {
        if (!securityProperties.getMockEnable()) {
            return null;
        }
        // 必须以 mockSecret 开头
        if (!token.startsWith(securityProperties.getMockSecret())) {
            return null;
        }
        // 构建模拟用户
        Integer userId = Integer.valueOf(token.substring(securityProperties.getMockSecret().length()));
        AuthUserDetail authUser = new AuthUserDetail();
        authUser.setId(userId);
        authUser.setUserType(userType);
        return authUser;
    }

    @SneakyThrows
    private AuthUserDetail buildLoginUserByHeader(HttpServletRequest request) {
        String authUserStr = request.getHeader(SecurityUtils.LOGIN_USER_HEADER);
        if (StrUtil.isEmpty(authUserStr)) {
            return null;
        }
        try {
            authUserStr = URLDecoder.decode(authUserStr, StandardCharsets.UTF_8.name()); // 解码，解决中文乱码问题
            return JsonUtils.parseObject(authUserStr, AuthUserDetail.class);
        } catch (Exception ex) {
            log.error("[buildLoginUserByHeader][解析 AuthUser({}) 发生异常]", authUserStr, ex);  ;
            throw ex;
        }
    }

}
