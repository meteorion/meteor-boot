package com.meteor.common.rule.core.easyrule;

import com.meteor.common.rule.core.RuleConfig;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * @author 钟宗兵
 * @since 1.0.0
 */
@Data
public class EasyRuleConfig implements RuleConfig {
    private String name;
    private int priority;
    private String description;
    private String condition;
    private List<String> actions;
}
