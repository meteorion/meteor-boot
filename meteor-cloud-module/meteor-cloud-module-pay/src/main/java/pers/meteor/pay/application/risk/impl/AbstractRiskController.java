package pers.meteor.pay.application.risk.impl;

import lombok.extern.slf4j.Slf4j;
import pers.meteor.pay.application.risk.RiskController;
import pers.meteor.pay.domain.risk.module.RiskControlParam;
import pers.meteor.pay.domain.risk.module.RiskControlResult;
import pers.meteor.pay.domain.risk.module.enums.RuleTypeEnum;

import java.util.EnumMap;
import java.util.List;
import java.util.function.BiFunction;

/**
 * @author meteor
 */
@Slf4j
public abstract class AbstractRiskController implements RiskController {
    private final String riskControlType;
    private final List<RiskControlParam> riskControlParams;
    private final EnumMap<RuleTypeEnum, BiFunction<Object, RiskControlParam, RiskControlResult>> riskControlService = new EnumMap<>(RuleTypeEnum.class);

    protected AbstractRiskController(String riskControlType, List<RiskControlParam> riskControlParams) {
        this.riskControlType = riskControlType;
        this.riskControlParams = riskControlParams;
        initRiskControlService();
    }

    @Override
    public String getRiskControlType() {
        return riskControlType;
    }

    @Override
    public RiskControlResult doRiskChecking(Object object) {
        RiskControlResult riskControlResult = preDoRiskChecking(object);
        for (RiskControlParam riskControlParam : riskControlParams) {
            RuleTypeEnum ruleType = riskControlParam.getRuleType();
            log.info("开始执行: {}({}) 风控", riskControlType, ruleType.getName());
            if (!riskControlParam.isEnabled()) {
                log.info("风控规则已关闭, 不执行");
                continue;
            }
            BiFunction<Object, RiskControlParam, RiskControlResult> biConsumer = riskControlService.get(ruleType);
            if (biConsumer != null) {
                log.info("风控参数: {}", riskControlParam.getValue());
                riskControlResult = biConsumer.apply(object, riskControlParam);
                log.info("风控结果: {}", riskControlResult);
            }
            if (!riskControlResult.isPass()) {
                break;
            }
        }
        return riskControlResult;
    }

    protected abstract void initRiskControlService();

    protected RiskControlResult preDoRiskChecking(Object object) {
        return RiskControlResult.pass(null);
    }

    protected void registerRiskController(RuleTypeEnum ruleType, BiFunction<Object, RiskControlParam, RiskControlResult> riskController) {
        this.riskControlService.put(ruleType, riskController);
    }
}
