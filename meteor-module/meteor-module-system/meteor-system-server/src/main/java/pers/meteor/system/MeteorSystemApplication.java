package pers.meteor.system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import pers.meteor.security.core.annotation.EnableResourceServer;
import pres.meteor.feign.annotation.EnableMeteorFeignClients;

@EnableMeteorFeignClients
@EnableResourceServer
@EnableDiscoveryClient
@SpringBootApplication
public class MeteorSystemApplication {

    public static void main(String[] args) {

        SpringApplication.run(MeteorSystemApplication.class, args);
    }

}
