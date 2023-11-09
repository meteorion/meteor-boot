package com.meteor.common.rule.core;

import java.util.List;
import java.util.Map;

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
    Long getId();

    /**
     * 执行规则
     *
     * @param groupKey  规则组id
     * @param facts     参数组
     * @return 执行结果
     */
    Map<String, Object> fire(String groupKey, Map<String, Object> facts);
}
