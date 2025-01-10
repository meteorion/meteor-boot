package pers.meteor.common.rule.core.easyrule;

import pers.meteor.common.rule.core.BasicRule;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.jeasy.rules.api.Facts;
import org.jeasy.rules.mvel.MVELRule;

import java.util.HashMap;

/**
 * @author 钟宗兵
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class EasyRule extends BasicRule {

    public MVELRule getMvelRule() {
        MVELRule mvelRule = new MVELRule()
                .name(name)
                .description(description)
                .priority(priority)
                .when(condition);
        if (actions == null || actions.isEmpty()) {
            mvelRule.then("");
        } else {
            actions.forEach(mvelRule::then);
        }
        return mvelRule;
    }

    @Override
    public boolean evaluate(HashMap<String, Object> facts) {
        Facts ruleFacts = new Facts();
        facts.forEach(ruleFacts::put);
        return getMvelRule().evaluate(ruleFacts);
    }
}
