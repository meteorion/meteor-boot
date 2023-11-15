package com.meteor.common.rule.core.easyrule;

import com.meteor.common.rule.core.BasicRule;
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
@EqualsAndHashCode(callSuper = false, exclude = "mvelRule")
public class EasyRule extends BasicRule {
    private final MVELRule mvelRule;

    public EasyRule() {
        this.mvelRule = new MVELRule()
                .name(name)
                .description(description)
                .priority(priority)
                .when(condition);
        if (actions == null || actions.isEmpty()) {
            this.mvelRule.then("");
        } else {
            actions.forEach(this.mvelRule::then);
        }
    }

    @Override
    public boolean evaluate(HashMap<String, Object> facts) {
        Facts ruleFacts = new Facts();
        facts.forEach(ruleFacts::put);
        return mvelRule.evaluate(ruleFacts);
    }
}
