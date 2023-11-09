package com.meteor.common.rule.core;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;

/**
 * @author 钟宗兵
 * @since 1.0.0
 */
@Slf4j
@Data
public abstract class AbstractRuleService<T extends RuleConfig> implements RuleService {
    /**
     * 配置id
     */
    protected final Long id;

    protected final Map<String, List<T>> ruleConfigs;

    public AbstractRuleService(Long id, Map<String, List<T>> ruleConfigs) {
        this.id = id;
        this.ruleConfigs = ruleConfigs;
    }

    protected final void init() {
        doInit();
        log.info("[init][配置初始化完成]");
    }

    protected abstract void doInit();
    protected abstract void doInit(String key);

    public final void refresh(String key, List<T> rules) {
        log.info("[refresh][配置({})发生变化，重新初始化]", key);
        ruleConfigs.put(key, rules);
        this.doInit(key);
    }

    @Override
    public Long getId() {
        return id;
    }
}
