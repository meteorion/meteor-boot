package pers.meteor.auth.endpoint;

import cn.hutool.core.util.StrUtil;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pers.meteor.common.entity.R;
import pers.meteor.common.util.captcha.core.CaptchaRequestForm;
import pers.meteor.common.util.captcha.core.CaptchaResponse;
import pers.meteor.common.util.captcha.core.client.CaptchaClient;
import pers.meteor.common.util.captcha.core.client.CaptchaClientFactory;
import pers.meteor.common.util.captcha.core.enums.CaptchaTypeEnum;

/**
 * 验证码相关的接口
 *
 * @author lengleng
 * @date 2022/6/27
 */
@RestController
@RequestMapping("/code")
@RequiredArgsConstructor
public class CaptchaCodeEndpoint {

	private final CaptchaClientFactory captchaClientFactory;
	/**
	 * 创建图形验证码
	 */
	@SneakyThrows
	@GetMapping("/image")
	public R<CaptchaResponse> image(CaptchaRequestForm captchaRequestForm) {
		if (StrUtil.isBlank(captchaRequestForm.getCaptchaType())) {
			captchaRequestForm.setCaptchaType(CaptchaTypeEnum.LINE.name());
		}
		CaptchaClient captchaClient = captchaClientFactory.getCaptchaClient(captchaRequestForm.getCaptchaType());
		CaptchaResponse captchaResponse = captchaClient.generate(captchaRequestForm);
		return R.ok(captchaResponse);
	}

}
