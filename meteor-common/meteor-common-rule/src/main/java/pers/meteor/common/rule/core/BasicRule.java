package pers.meteor.common.rule.core;

import pers.meteor.common.rule.api.Rule;
import lombok.Data;

import java.util.HashMap;
import java.util.List;

/**
 * @author 钟宗兵
 * @since 1.0.0
 */
@Data
public class BasicRule implements Rule {
    protected String name;
    protected int priority;
    protected boolean enabled = true;
    protected String description;
    protected String condition;
    protected List<String> actions;

    @Override
    public boolean evaluate(HashMap<String, Object> facts) {
        return true;
    }
}
