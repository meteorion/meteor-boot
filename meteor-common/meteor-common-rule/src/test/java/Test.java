import pers.meteor.common.rule.core.easyrule.EasyRule;

import java.util.*;

/**
 * @author 钟宗兵
 * @since 1.0.0
 */
public class Test {

    public void test() {
        EasyRule ruleConfig1 = new EasyRule();
        ruleConfig1.setName("1");
        ruleConfig1.setDescription("1");
        ruleConfig1.setPriority(1);
        ruleConfig1.setCondition("number < 18");
        ruleConfig1.setActions(Collections.singletonList("result=1;map.put('result', '未成年')"));

        EasyRule ruleConfig2 = new EasyRule();
        ruleConfig2.setName("2");
        ruleConfig2.setDescription("2");
        ruleConfig2.setPriority(2);
        ruleConfig2.setCondition("number >= 18");
        ruleConfig2.setActions(Collections.singletonList("result='1';map.put('result', '成年')"));

        ArrayList<EasyRule> configs = new ArrayList<>();
        configs.add(ruleConfig1);
        configs.add(ruleConfig2);

    }
}
