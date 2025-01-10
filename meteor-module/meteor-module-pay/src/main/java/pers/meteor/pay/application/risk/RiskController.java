package pers.meteor.pay.application.risk;

import pers.meteor.pay.domain.risk.module.RiskControlResult;

/**
 * @author meteor
 */
public interface RiskController {
    /**
     * 获取风控类型
     *
     * @return /
     */
    String getRiskControlType();

    /**
     * 执行风控检查
     *
     * @param object /
     * @return /
     */
    RiskControlResult doRiskChecking(Object object);
}
