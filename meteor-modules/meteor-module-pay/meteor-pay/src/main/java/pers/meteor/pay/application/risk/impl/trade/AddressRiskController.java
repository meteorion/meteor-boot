package pers.meteor.pay.application.risk.impl.trade;

import pers.meteor.pay.application.risk.impl.AbstractRiskController;
import pers.meteor.pay.domain.risk.module.RiskControlParam;
import pers.meteor.pay.domain.risk.module.RiskControlResult;
import pers.meteor.pay.domain.risk.module.enums.RiskControlTypeEnum;
import pers.meteor.pay.domain.risk.module.enums.RuleTypeEnum;

import java.util.List;

/**
 * 交易地址风控
 * @author meteor
 */
public class AddressRiskController extends AbstractRiskController {

    protected AddressRiskController(List<RiskControlParam> riskControlParams) {
        super(RiskControlTypeEnum.TRADE_ADDRESS.name(), riskControlParams);
    }

    @Override
    protected void initRiskControlService() {
        registerRiskController(RuleTypeEnum.REGION, this::doRegionRisk);
        registerRiskController(RuleTypeEnum.IP, this::doIpRisk);
    }

    /**
     * 地区风控
     *
     * @param object    /
     * @param riskControlParam /
     * @return /
     */
    private RiskControlResult doRegionRisk(Object object, RiskControlParam riskControlParam) {
        RuleTypeEnum ruleType = riskControlParam.getRuleType();

        return RiskControlResult.pass(ruleType.getName());
    }

    /**
     * IP风控
     *
     * @param object    /
     * @param riskControlParam /
     * @return /
     */
    private RiskControlResult doIpRisk(Object object, RiskControlParam riskControlParam) {
        RuleTypeEnum ruleType = riskControlParam.getRuleType();

        return RiskControlResult.pass(ruleType.getName());
    }
}
