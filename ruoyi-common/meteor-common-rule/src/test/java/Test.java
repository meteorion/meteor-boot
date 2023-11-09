import com.meteor.common.rule.core.easyrule.EasyRuleConfig;
import com.meteor.common.rule.core.easyrule.EasyRuleServiceImpl;

import java.util.*;

/**
 * @author 钟宗兵
 * @since 1.0.0
 */
public class Test {

    public void test() {
        EasyRuleConfig ruleConfig1 = new EasyRuleConfig();
        ruleConfig1.setName("1");
        ruleConfig1.setDescription("1");
        ruleConfig1.setPriority(1);
        ruleConfig1.setCondition("number < 18");
        ruleConfig1.setActions(Collections.singletonList("result=1;map.put('result', '未成年')"));

        EasyRuleConfig ruleConfig2 = new EasyRuleConfig();
        ruleConfig2.setName("2");
        ruleConfig2.setDescription("2");
        ruleConfig2.setPriority(2);
        ruleConfig2.setCondition("number >= 18");
        ruleConfig2.setActions(Collections.singletonList("result='1';map.put('result', '成年')"));

        ArrayList<EasyRuleConfig> configs = new ArrayList<>();
        configs.add(ruleConfig1);
        configs.add(ruleConfig2);

        HashMap<String, List<EasyRuleConfig>> rules = new HashMap<>();
        rules.put("age", configs);

        EasyRuleServiceImpl ruleService = new EasyRuleServiceImpl(1L, rules);

        HashMap<String, Object> facts = new HashMap<>();
        facts.put("number", 19);

        facts.put("result", "");
        facts.put("map", new HashMap<>());

        Map<String, Object> result = ruleService.fire("age", facts);
        System.out.println(result.get("result"));
    }
}
