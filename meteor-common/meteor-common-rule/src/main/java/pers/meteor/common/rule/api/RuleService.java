package pers.meteor.common.rule.api;

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
    Integer getId();

    /**
     * 执行规则
     *
     * @param ruleFact 规则参数
     * @return /
     */
    Map<String, Object> run(RuleFact ruleFact);
}
