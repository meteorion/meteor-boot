package com.meteor.common.rule.api;

import com.meteor.common.rule.core.BasicRule;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author 钟宗兵
 * @since 1.0.0
 */
public interface RuleService {

    /**
     * 规则id
     *
     * @return /
     */
    Integer getId();

    /**
     * 执行规则
     *
     * @param ruleFact 规则参数
     * @return /
     */
    Map<String, Object> run(RuleFact ruleFact);
}
