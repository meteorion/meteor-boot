package pers.meteor.common.rule.api;

import lombok.Data;

import java.util.HashMap;
import java.util.Set;

/**
 * @author 钟宗兵
 * @since 1.0.0
 */
@Data
public class RuleFact {
    private final HashMap<String, Object> facts = new HashMap<>();
    private String groupName;
    private Set<String> resultKeys;

    public RuleFact add(String name, Object value) {
        this.add(name, value, false);
        return this;
    }

    public RuleFact add(String name, Object value, boolean result) {
        facts.put(name, value);
        if (result) {
            resultKeys.add(name);
        }
        return this;
    }

    public RuleFact remove(String name) {
        facts.remove(name);
        return this;
    }

}
