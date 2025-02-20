package pers.meteor.auth.support.filter;

import cn.hutool.core.util.StrUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import pers.meteor.auth.config.AuthSecurityConfigProperties;
import pers.meteor.common.constant.CacheConstants;
import pers.meteor.common.constant.SecurityConstants;
import pers.meteor.common.exception.ValidateCodeException;
import pers.meteor.common.util.SpringContextHolder;
import pers.meteor.common.util.WebUtils;

import java.io.IOException;
import java.util.Optional;

/**
 * @author lbw
 * @date 2024-01-06
 * <p>
 * 登录前置处理器： 前端密码传输密文解密，验证码处理
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ValidateCodeFilter extends OncePerRequestFilter {

	private final AuthSecurityConfigProperties authSecurityConfigProperties;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String requestUrl = request.getServletPath();

		// 不是登录URL 请求直接跳过
		if (!SecurityConstants.OAUTH_TOKEN_URL.equals(requestUrl)) {
			filterChain.doFilter(request, response);
			return;
		}

		// 如果登录URL 但是刷新token的请求，直接向下执行
		String grantType = request.getParameter(OAuth2ParameterNames.GRANT_TYPE);
		if (StrUtil.equals(SecurityConstants.REFRESH_TOKEN, grantType)) {
			filterChain.doFilter(request, response);
			return;
		}

		// 如果是密码模式 && 客户端不需要校验验证码
		boolean isIgnoreClient = authSecurityConfigProperties.getIgnoreClients().contains(WebUtils.getClientId());
		if (StrUtil.equals(SecurityConstants.PASSWORD, grantType) && isIgnoreClient) {
			filterChain.doFilter(request, response);
			return;
		}

		// 校验验证码 1. 客户端开启验证码 2. 短信模式
		try {
			checkCode();
			filterChain.doFilter(request, response);
		}
		catch (ValidateCodeException validateCodeException) {
			throw new OAuth2AuthenticationException(validateCodeException.getMessage());
		}
	}

	/**
	 * 校验验证码
	 */
	private void checkCode() throws ValidateCodeException {
		Optional<HttpServletRequest> request = WebUtils.getRequest();
		String code = request.get().getParameter("code");

		if (StrUtil.isBlank(code)) {
			throw new ValidateCodeException("验证码不能为空");
		}

		String randomStr = request.get().getParameter("randomStr");

		// https://gitee.com/log4j/pig/issues/IWA0D
		String mobile = request.get().getParameter("mobile");
		if (StrUtil.isNotBlank(mobile)) {
			randomStr = mobile;
		}

		String key = CacheConstants.DEFAULT_CODE_KEY + randomStr;
		RedisTemplate<String, String> redisTemplate = SpringContextHolder.getBean(RedisTemplate.class);
		if (Boolean.FALSE.equals(redisTemplate.hasKey(key))) {
			throw new ValidateCodeException("验证码不合法");
		}

		Object codeObj = redisTemplate.opsForValue().get(key);

		if (codeObj == null) {
			throw new ValidateCodeException("验证码不合法");
		}

		String saveCode = codeObj.toString();
		if (StrUtil.isBlank(saveCode)) {
			redisTemplate.delete(key);
			throw new ValidateCodeException("验证码不合法");
		}

		if (!StrUtil.equals(saveCode, code)) {
			redisTemplate.delete(key);
			throw new ValidateCodeException("验证码不合法");
		}

		redisTemplate.delete(key);
	}

}
