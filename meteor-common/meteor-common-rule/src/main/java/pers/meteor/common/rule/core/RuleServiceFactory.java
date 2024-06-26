package pers.meteor.common.rule.core;


import pers.meteor.common.rule.api.RuleLoader;
import pers.meteor.common.rule.api.RuleService;

/**
 * @author meteor
 */
public interface RuleServiceFactory {
    /**
     * 获得规则服务
     *
     * @param configId 配置编号
     * @return 规则服务
     */
    RuleService getRuleService(Integer configId);

    /**
     * 创建规则服务
     *
     * @param serviceId /
     * @param ruleLoader /
     */
    <T extends BasicRule> void createRuleService(Integer serviceId, RuleLoader<T> ruleLoader);
}
