package pers.meteor.common.rule.api;

import pers.meteor.common.rule.core.BasicRule;
import lombok.Data;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author 钟宗兵
 * @since 1.0.0
 */
@Data
public class RuleGroup<T extends BasicRule> {
    private String groupName;
    private boolean enabled;
    /**
     * 规则触发类型
     * @see TriggerMode
     */
    private String triggerMode;

    private List<T> rules;

    public void addRule(T rule) {
        rules.add(rule);
        sortRules();
    }

    public void addRules(Set<T> rules) {
        this.rules.addAll(rules);
        sortRules();
    }

    /**
     * 获取触发执行的规则
     *
     * @return /
     */
    public List<T> getTriggerRules(HashMap<String, Object> facts) {
        TriggerMode mode = this.getMode();

        List<T> rules = new ArrayList<>();

        switch (mode) {
            case ALL:
                rules = this.rules;
                break;
            case CHAIN:
                rules = getChainModeRules(facts);
                break;
            case SINGLE:
                rules = getSingleModeRules(facts);
                break;
        }

        return rules;
    }

    public TriggerMode getMode() {
        TriggerMode mode;
        if (null == triggerMode || triggerMode.trim().isEmpty()) {
            mode = TriggerMode.ALL;
        } else {
            mode = TriggerMode.valueOf(triggerMode);
        }
        return mode;
    }

    public List<T> getChainModeRules(HashMap<String, Object> facts) {
        List<T> chainRules = new ArrayList<>();
        for (T rule : this.rules) {
            if (!rule.isEnabled()) {
                continue;
            }
            if (!rule.evaluate(facts)) {
                break;
            }
            chainRules.add(rule);
        }
        return chainRules;
    }

    public List<T> getSingleModeRules(HashMap<String, Object> facts) {
        List<T> singleRules = new ArrayList<>();
        for (T rule : this.rules) {
            if (rule.isEnabled() && rule.evaluate(facts)) {
                singleRules.add(rule);
                break;
            }
        }
        return singleRules;
    }

    private void sortRules() {
        this.rules = this.rules.stream()
                .sorted(Comparator.comparing(Rule::getPriority))
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
