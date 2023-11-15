package com.meteor.common.rule.api;

import com.meteor.common.rule.core.BasicRule;

import java.util.Set;

/**
 * @author 钟宗兵
 * @since 1.0.0
 */
public interface RuleLoader<T extends BasicRule> {

    String getVersion();

    Set<RuleGroup<T>> load();
}
