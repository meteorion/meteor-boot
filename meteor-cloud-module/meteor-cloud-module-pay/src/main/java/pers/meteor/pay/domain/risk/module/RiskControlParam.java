package pers.meteor.pay.domain.risk.module;

import lombok.Data;
import pers.meteor.pay.domain.risk.module.enums.RuleTypeEnum;

/**
 * @author meteor
 */
@Data
public class RiskControlParam {
    /**
     * 参数组id
     */
    private String groupId;
    /**
     * 风控规则
     */
    private RuleTypeEnum ruleType;
    /**
     * 参数值
     */
    private String value;
    /**
     * 顺序
     */
    private Integer order;
    /**
     * 是否启用
     */
    private boolean enabled;
}
