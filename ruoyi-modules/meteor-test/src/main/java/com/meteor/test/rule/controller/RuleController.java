package com.meteor.test.rule.controller;

import com.meteor.common.rule.api.RuleFact;
import com.meteor.common.rule.api.RuleService;
import com.meteor.common.rule.core.EasyRuleNacosRuleLoader;
import com.meteor.common.rule.core.RuleServiceFactory;
import com.ruoyi.common.core.domain.R;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 钟宗兵
 * @since 1.0.0
 */
@RestController
@RequiredArgsConstructor
public class RuleController {

    private final RuleServiceFactory ruleServiceFactory;
    private final EasyRuleNacosRuleLoader easyRuleNacosRuleLoader;
    private RuleService ruleService;

    @PostConstruct
    public void init() {
        ruleServiceFactory.createRuleService(1, easyRuleNacosRuleLoader);
        ruleService = ruleServiceFactory.getRuleService(1);
    }


    @PostMapping("fire")
    public R<Object> fire(@RequestBody Map<String, Object> params) {
        RuleFact ruleFact = new RuleFact();
        ruleFact.setGroupName("rule_group_age");
        params.forEach(ruleFact::add);
        ruleFact.add("result", new HashMap<>());
        Map<String, Object> result = ruleService.run(ruleFact);
        return R.ok(result.get("result"));
    }
}
