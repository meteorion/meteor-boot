package pers.meteor.pay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import pers.meteor.common.security.annotation.EnableCustomConfig;
import pers.meteor.common.security.annotation.EnableRyFeignClients;

/**
 * @author meteor
 */
@EnableCustomConfig
@EnableRyFeignClients
@SpringBootApplication
public class PayApplication {
    public static void main(String[] args)
    {
        SpringApplication.run(PayApplication.class, args);
    }
}
