package com.meteor.common.rule.core;

import cn.hutool.core.util.ReflectUtil;
import com.meteor.common.rule.api.RuleLoader;
import com.meteor.common.rule.api.RuleService;
import com.meteor.common.rule.api.RuleServiceType;
import com.meteor.common.rule.exception.RuleException;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author 钟宗兵
 * @since 1.0.0
 */
public class RuleServiceFactoryImpl implements RuleServiceFactory {
    /**
     * 规则服务 Map
     * key：配置编号
     */
    private final ConcurrentMap<Integer, RuleService> ruleServices = new ConcurrentHashMap<>();

    @Override
    public RuleService getRuleService(Integer serviceId) {
        RuleService ruleService = ruleServices.get(serviceId);
        if (ruleService == null) {
            throw new RuleException("规则服务未初始化");
        }
        return ruleService;
    }

    @Override
    public <T extends BasicRule> void createRuleService(Integer serviceId, RuleLoader<T> ruleLoader) {
        RuleServiceType serviceType = RuleServiceType.getByServiceId(serviceId);
        if (serviceType == null) {
            throw new RuleException("创建规则服务失败, 配置id不支持");
        }
        RuleService ruleService = ReflectUtil.newInstance(serviceType.getRuleServiceClass(), serviceId, ruleLoader);
        ruleServices.put(serviceId, ruleService);
    }
}
