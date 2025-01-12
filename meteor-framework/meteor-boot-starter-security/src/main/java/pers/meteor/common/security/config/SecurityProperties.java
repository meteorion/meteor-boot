package pers.meteor.common.security.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

/**
 * @author meteor
 */
@ConfigurationProperties("meteor.security")
@Validated
@Data
public class SecurityProperties {

    private JwtProperties jwt;

    @Data
    public static class JwtProperties {
        /**
         * 密钥
         */
        private String secret;
        /**
         * 访问令牌的有效期
         */
        private Integer accessTokenValiditySeconds;
        /**
         * 刷新令牌的有效期
         */
        private Integer refreshTokenValiditySeconds;
    }
}
