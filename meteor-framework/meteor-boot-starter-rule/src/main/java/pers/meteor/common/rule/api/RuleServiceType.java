package pers.meteor.common.rule.api;

import pers.meteor.common.rule.core.AbstractRuleService;
import pers.meteor.common.rule.core.easyrule.EasyRuleServiceImpl;
import lombok.Getter;

import java.util.Objects;

/**
 * @author 钟宗兵
 * @since 1.0.0
 */
@Getter
public enum RuleServiceType {
    EASY_RULE(1, EasyRuleServiceImpl.class)
    ;
    /**
     * 存储器
     */
    private final Integer serviceId;

    /**
     * 客户端类
     */
    private final Class<? extends AbstractRuleService<?>> ruleServiceClass;

    RuleServiceType(Integer serviceId, Class<? extends AbstractRuleService<?>> ruleServiceClass) {
        this.serviceId = serviceId;
        this.ruleServiceClass = ruleServiceClass;
    }

    public static RuleServiceType getByServiceId(Integer serviceId) {
        for (RuleServiceType service : values()) {
            if (Objects.equals(service.serviceId, serviceId)) {
                return service;
            }

        }
        return null;
    }
}
