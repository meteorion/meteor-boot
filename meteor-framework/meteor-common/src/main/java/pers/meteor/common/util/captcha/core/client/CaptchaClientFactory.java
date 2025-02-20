package pers.meteor.common.util.captcha.core.client;

/**
 * @author meteor
 */
public interface CaptchaClientFactory {
    /**
     * 获得 Client
     *
     * @param captchaType 渠道编码
     * @return 短信 Client
     */
    CaptchaClient getCaptchaClient(String captchaType);

    /**
     * 创建 Client
     *
     * @param captchaClient 配置对象
     */
    void registerCaptchaClient(CaptchaClient captchaClient);
}
