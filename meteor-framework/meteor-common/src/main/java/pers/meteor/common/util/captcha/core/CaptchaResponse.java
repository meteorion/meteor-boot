package pers.meteor.common.util.captcha.core;

import lombok.Builder;
import lombok.Data;

/**
 * 验证码响应对象
 *
 * @author Ray Hao
 * @since 2023/03/24
 */
@Data
@Builder
public class CaptchaResponse {

    private String captchaKey;

    private String captchaBase64;

}
