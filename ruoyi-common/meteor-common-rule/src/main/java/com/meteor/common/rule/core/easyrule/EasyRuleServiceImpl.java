package com.meteor.common.rule.core.easyrule;

import com.meteor.common.rule.core.AbstractRuleService;
import org.jeasy.rules.api.*;
import org.jeasy.rules.core.DefaultRulesEngine;
import org.jeasy.rules.mvel.MVELRule;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author 钟宗兵
 * @since 1.0.0
 */
public class EasyRuleServiceImpl extends AbstractRuleService<EasyRuleConfig> {

    private final ConcurrentHashMap<String, Rules> rulesMap = new ConcurrentHashMap<>();

    public EasyRuleServiceImpl(Long id, Map<String, List<EasyRuleConfig>> ruleConfigs) {
        super(id, ruleConfigs);
        this.doInit();
    }

    @Override
    protected void doInit() {
        if (ruleConfigs == null) {
            return;
        }
        for (String key : ruleConfigs.keySet()) {
            doInit(key);
        }
    }

    @Override
    protected void doInit(String key) {
        List<EasyRuleConfig> configs = ruleConfigs.get(key);
        if (configs == null) {
            return;
        }
        Rules rules = new Rules();
        for (EasyRuleConfig config : configs) {
            MVELRule rule = new MVELRule()
                    .name(config.getName())
                    .description(config.getDescription())
                    .priority(config.getPriority())
                    .when(config.getCondition());
            List<String> actions = config.getActions();
            if (CollectionUtils.isEmpty(actions)) {
                rule.then("");
            } else {
                actions.forEach(rule::then);
            }

            rules.register(rule);
        }
        rulesMap.put(key, rules);
    }

    @Override
    public Map<String, Object> fire(String groupKey, Map<String, Object> facts) {
        Rules rules = rulesMap.get(groupKey);
        if (rules == null) {
            return null;
        }
        Facts ruleFacts = new Facts();
        facts.forEach(ruleFacts::put);
        // 构建执行器
        RulesEngine rulesEngine = new DefaultRulesEngine();
        rulesEngine.fire(rules, ruleFacts);

        return ruleFacts.asMap();
    }
}
