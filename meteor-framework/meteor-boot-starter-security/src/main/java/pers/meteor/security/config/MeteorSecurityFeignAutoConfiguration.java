package pers.meteor.security.config;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import pers.meteor.auth.api.AuthTokenApi;
import pers.meteor.security.core.interceptor.FeignRequestInterceptor;
import pers.meteor.system.api.permission.PermissionApi;

/**
 * Security 使用到 Feign 的配置项
 *
 * @author 芋道源码
 */
@AutoConfiguration
@EnableFeignClients(clients = {PermissionApi.class, AuthTokenApi.class})
public class MeteorSecurityFeignAutoConfiguration {
    @Bean
    public FeignRequestInterceptor loginUserRequestInterceptor() {
        return new FeignRequestInterceptor();
    }

}
