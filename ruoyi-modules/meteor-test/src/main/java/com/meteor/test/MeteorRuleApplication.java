package com.meteor.test;

import com.meteor.test.rule.service.MeteorRuleConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * @author 钟宗兵
 * @since 1.0.0
 */
@EnableConfigurationProperties(MeteorRuleConfig.class)
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class MeteorRuleApplication {
    public static void main(String[] args) {
        SpringApplication.run(MeteorRuleApplication.class, args);
    }
}