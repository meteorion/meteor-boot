package pers.meteor.common.util.captcha.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import pers.meteor.common.util.captcha.core.enums.CaptchaTypeEnum;

/**
 * 验证码 属性配置
 *
 * @author haoxr
 * @since 2023/11/24
 */
@ConfigurationProperties(prefix = "captcha")
@Data
public class CaptchaProperties {

    /**
     * 验证码类型  circle-圆圈干扰验证码|gif-Gif验证码|line-干扰线验证码|shear-扭曲干扰验证码
     */
    private String type = CaptchaTypeEnum.CIRCLE.name();

    /**
     * 是否启用验证码
     */
    private boolean enabled = true;

    /**
     * 验证码图片宽度
     */
    private int width = 120;
    /**
     * 验证码图片高度
     */
    private int height = 40;

    /**
     * 干扰线数量
     */
    private int interfereCount = 2;

    /**
     * 文本透明度
     */
    private Float textAlpha = 0.8F;

    /**
     * 验证码过期时间，单位：秒
     */
    private Long expireSeconds = 120L;

    /**
     * 验证码字符配置
     */
    private CodeProperties code = new CodeProperties("math", 1);

    /**
     * 验证码字体
     */
    private FontProperties font = new FontProperties("SansSerif", 1, 24);

    /**
     * 验证码字符配置
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CodeProperties {
        /**
         * 验证码字符类型 math-算术|random-随机字符串
         */
        private String type;
        /**
         * 验证码字符长度，type=算术时，表示运算位数(1:个位数 2:十位数)；type=随机字符时，表示字符个数
         */
        private int length;
    }

    /**
     * 验证码字体配置
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FontProperties {
        /**
         * 字体名称
         */
        private String name;
        /**
         * 字体样式  0-普通|1-粗体|2-斜体
         */
        private int weight;
        /**
         * 字体大小
         */
        private int size;
    }


}
