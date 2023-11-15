package com.meteor.common.rule.core.easyrule;

import com.meteor.common.rule.api.RuleLoader;
import com.meteor.common.rule.core.AbstractRuleService;
import org.jeasy.rules.api.*;
import org.jeasy.rules.core.DefaultRulesEngine;
import org.jeasy.rules.mvel.MVELRule;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author 钟宗兵
 * @since 1.0.0
 */
public class EasyRuleServiceImpl extends AbstractRuleService<EasyRule> {

    public EasyRuleServiceImpl(Integer id, RuleLoader<EasyRule> ruleLoader) {
        super(id, ruleLoader);
        super.init();
    }

    @Override
    protected void doInit() {
        // pass
    }

    @Override
    public Map<String, Object> run(List<EasyRule> rules, HashMap<String, Object> facts) {
        Set<MVELRule> mvelRules = rules.stream().map(EasyRule::getMvelRule).collect(Collectors.toSet());
        Rules easyRules = new Rules();
        for (MVELRule mvelRule : mvelRules) {
            easyRules.register(mvelRule);
        }

        Facts ruleFacts = new Facts();
        facts.forEach(ruleFacts::put);

        RulesEngine rulesEngine = new DefaultRulesEngine();
        rulesEngine.fire(easyRules, ruleFacts);

        return ruleFacts.asMap();
    }
}
