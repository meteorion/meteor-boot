package pers.meteor.common.rule.core.easyrule;

import pers.meteor.common.rule.api.RuleLoader;
import pers.meteor.common.rule.core.AbstractRuleService;
import org.jeasy.rules.api.Facts;
import org.jeasy.rules.api.Rules;
import org.jeasy.rules.api.RulesEngine;
import org.jeasy.rules.core.DefaultRulesEngine;
import org.jeasy.rules.mvel.MVELRule;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
        List<MVELRule> mvelRules = rules.stream().map(EasyRule::getMvelRule).collect(Collectors.toList());
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

    @Override
    protected Map<String, Object> run(EasyRule rule, HashMap<String, Object> facts) {
        MVELRule mvelRule = rule.getMvelRule();
        Rules easyRules = new Rules();
        easyRules.register(mvelRule);

        Facts ruleFacts = new Facts();
        facts.forEach(ruleFacts::put);

        RulesEngine rulesEngine = new DefaultRulesEngine();
        rulesEngine.fire(easyRules, ruleFacts);

        return ruleFacts.asMap();
    }
}
