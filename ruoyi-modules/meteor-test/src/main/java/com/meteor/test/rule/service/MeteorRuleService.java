package com.meteor.test.rule.service;

import com.meteor.common.rule.core.easyrule.EasyRuleConfig;
import com.meteor.common.rule.core.easyrule.EasyRuleServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 钟宗兵
 * @since 1.0.0
 */
@Service
@RequiredArgsConstructor
public class MeteorRuleService {

    private final MeteorRuleConfig ruleConfig;
    private EasyRuleServiceImpl easyRuleService;

    private int localVersion;

    @PostConstruct
    public void init() {
        HashMap<String, List<EasyRuleConfig>> rules = new HashMap<>();
        rules.put("rule1", ruleConfig.getRules());
        easyRuleService = new EasyRuleServiceImpl(1L, rules);
        localVersion = ruleConfig.getVersion();
    }

    public Object fire(Map<String, Object> params) {

        if (localVersion != ruleConfig.getVersion()) {
            easyRuleService.refresh("rule1", ruleConfig.getRules());
        }

        Demo demo = new Demo();

        params.put("demo", demo);
        params.put("result", new HashMap<>());

        Map<String, Object> result = easyRuleService.fire((String) params.get("groupName"), params);

        return result.get("result");
    }

    public static class Demo {
        public String test() {
            return "1213";
        }
    }


}
