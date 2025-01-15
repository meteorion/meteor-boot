package pers.meteor.security.core.interceptor;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import lombok.extern.slf4j.Slf4j;
import pers.meteor.auth.api.dto.AuthUserDetail;
import pers.meteor.common.constant.SecurityConstants;
import pers.meteor.common.exception.ServiceException;
import pers.meteor.common.utils.ip.IpUtils;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import pers.meteor.common.utils.json.JsonUtils;
import pers.meteor.security.core.utils.SecurityUtils;

/**
 * feign 请求拦截器
 *
 * @author meteor
 */
@Slf4j
public class FeignRequestInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate requestTemplate) {
        AuthUserDetail authUser = SecurityUtils.getAuthUser();
        if (authUser == null) {
            return;
        }
        try {
            // 传递用户信息请求头，防止丢失
            requestTemplate.header(SecurityConstants.DETAILS_USER_ID, String.valueOf(authUser.getId()));
            requestTemplate.header(SecurityConstants.USER_KEY, authUser.getMobile());
            requestTemplate.header(SecurityConstants.DETAILS_USERNAME, authUser.getUserName());
            requestTemplate.header(SecurityConstants.AUTHORIZATION_HEADER, SecurityUtils.getToken());
            String userStr = JsonUtils.toJsonString(authUser);
            userStr = URLEncoder.encode(userStr, StandardCharsets.UTF_8.name()); // 编码，避免中文乱码
            requestTemplate.header(SecurityUtils.LOGIN_USER_HEADER, userStr);
            // 配置客户端IP
            requestTemplate.header("X-Forwarded-For", IpUtils.getIpAddr());
        }  catch (Exception ex) {
            log.error("[apply][序列化 LoginUser({}) 发生异常]", authUser, ex);
            throw new ServiceException("序列化异常");
        }
    }
}
