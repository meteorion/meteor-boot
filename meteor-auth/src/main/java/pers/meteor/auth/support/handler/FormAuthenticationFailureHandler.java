package pers.meteor.auth.support.handler;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.http.HttpUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import pers.meteor.common.util.WebUtils;

import java.io.IOException;

/**
 * @author lengleng
 * @date 2022-06-02
 * <p>
 * 表单登录失败处理逻辑
 */
@Slf4j
public class FormAuthenticationFailureHandler implements AuthenticationFailureHandler {

	/**
	 * Called when an authentication attempt fails.
	 * @param request the request during which the authentication attempt occurred.
	 * @param response the response.
	 * @param exception the exception which was thrown to reject the authentication
	 */
	@Override
	@SneakyThrows
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) {
		log.debug("表单登录失败:{}", exception.getLocalizedMessage());

		// 获取当前请求的context-path
		String contextPath = request.getContextPath();

		// 构建重定向URL，加入context-path
		String url = HttpUtil.encodeParams(
				String.format("%s/token/login?error=%s", contextPath, exception.getMessage()),
				CharsetUtil.CHARSET_UTF_8);

		try {
			WebUtils.getResponse().sendRedirect(url);
		}
		catch (IOException e) {
			log.error("重定向失败", e);
		}
	}

}
