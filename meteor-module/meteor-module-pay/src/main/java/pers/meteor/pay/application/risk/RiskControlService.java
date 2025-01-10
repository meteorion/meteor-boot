package pers.meteor.pay.application.risk;

import pers.meteor.pay.domain.risk.module.RiskControlResult;
import pers.meteor.pay.domain.risk.module.enums.OperationTypeEnum;
import pers.meteor.pay.domain.risk.module.enums.RiskControlTypeEnum;

import java.util.List;

/**
 * @author meteor
 */
public interface RiskControlService {
    /**
     * 刷新风控配置
     */
    void refreshRiskControl();

    /**
     * 执行指定风控
     *
     * @param operationType 操作类型
     * @param object 风控类型
     * @param refreshConfig 刷新配置
     */
    RiskControlResult doRiskChecking(OperationTypeEnum operationType, Object object, boolean refreshConfig);

    /**
     * 执行指定风控
     *
     * @param operationType 操作类型
     * @param object 风控类型
     * @param refreshConfig 刷新配置
     */
    void doRiskChecking(OperationTypeEnum operationType, List<RiskControlTypeEnum> controlTypes, Object object, boolean refreshConfig);

    /**
     * 执行指定风控
     *
     * @param operationType 操作类型
     * @param controlType 风控类型
     * @param object 风控参数
     * @return /
     */
    RiskControlResult doRiskChecking(OperationTypeEnum operationType, RiskControlTypeEnum controlType, Object object);
}
