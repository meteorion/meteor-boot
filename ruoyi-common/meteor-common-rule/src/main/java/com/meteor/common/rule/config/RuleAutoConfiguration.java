package com.meteor.common.rule.config;

import com.meteor.common.rule.core.EasyRuleNacosRuleLoader;
import com.meteor.common.rule.core.RuleServiceFactory;
import com.meteor.common.rule.core.RuleServiceFactoryImpl;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;

/**
 * @author 钟宗兵
 * @since 1.0.0
 */
@AutoConfiguration
public class RuleAutoConfiguration {

    @Bean
    public RuleServiceFactory getRuleServiceFactory() {
        return new RuleServiceFactoryImpl();
    }

    @Bean
    @ConditionalOnProperty(name = "rule.nacos.enabled", havingValue = "true")
    public EasyRuleNacosRuleLoader getEasyRuleNacosRuleLoader() {
        return new EasyRuleNacosRuleLoader();
    }
}
