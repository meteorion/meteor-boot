package pers.meteor.pay.application.risk;

import pers.meteor.pay.domain.risk.module.RiskControl;
import pers.meteor.pay.domain.risk.module.enums.RiskControlTypeEnum;

import java.util.Collection;

/**
 * @author meteor
 */
public interface RiskControllerFactory {

    /**
     * 清空规则
     */
    void clear();

    /**
     * 注册风控服务
     *
     * @param riskControl /
     */
    void registerRiskControl(RiskControl riskControl);

    /**
     * 获取风控服务
     *
     * @param riskControlType /
     * @return /
     */
    RiskController getRiskControlService(String operationType, RiskControlTypeEnum riskControlType);

    /**
     * 获取风控服务
     *
     * @param operationType /
     * @return /
     */
    Collection<RiskController> getRiskControlService(String operationType);
}
