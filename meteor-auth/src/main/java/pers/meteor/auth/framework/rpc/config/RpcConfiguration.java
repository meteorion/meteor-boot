package pers.meteor.auth.framework.rpc.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;
import pers.meteor.system.api.user.AdminUserApi;

@Configuration(proxyBeanMethods = false)
@EnableFeignClients(clients = {AdminUserApi.class})
public class RpcConfiguration {
}
