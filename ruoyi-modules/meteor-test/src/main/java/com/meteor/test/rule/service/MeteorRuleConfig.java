package com.meteor.test.rule.service;

import com.meteor.common.rule.core.easyrule.EasyRuleConfig;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

import java.util.List;

/**
 * @author 钟宗兵
 * @since 1.0.0
 */
@Data
@RefreshScope
@ConfigurationProperties("rules.rule1")
public class MeteorRuleConfig {
    private int version;
    private List<EasyRuleConfig> rules;
}

