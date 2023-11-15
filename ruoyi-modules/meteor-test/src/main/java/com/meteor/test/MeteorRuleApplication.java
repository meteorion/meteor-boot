package com.meteor.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * @author 钟宗兵
 * @since 1.0.0
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class MeteorRuleApplication {
    public static void main(String[] args) {
        SpringApplication.run(MeteorRuleApplication.class, args);
    }
}