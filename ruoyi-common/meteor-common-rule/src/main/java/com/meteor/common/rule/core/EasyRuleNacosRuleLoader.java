package com.meteor.common.rule.core;

import com.meteor.common.rule.api.RuleGroup;
import com.meteor.common.rule.api.RuleLoader;
import com.meteor.common.rule.core.easyrule.EasyRule;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author 钟宗兵
 * @since 1.0.0
 */
@Data
@RefreshScope
@ConfigurationProperties(prefix = "rule")
public class EasyRuleNacosRuleLoader implements RuleLoader<EasyRule> {
    private String version;
    private List<RuleGroup<EasyRule>> ruleGroups;

    @Override
    public Set<RuleGroup<EasyRule>> load() {
        return new HashSet<>(ruleGroups);
    }
}
