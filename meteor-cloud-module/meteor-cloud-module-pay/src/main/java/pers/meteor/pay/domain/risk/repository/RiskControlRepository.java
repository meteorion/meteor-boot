package pers.meteor.pay.domain.risk.repository;

import pers.meteor.pay.domain.risk.module.RiskControl;
import pers.meteor.pay.domain.risk.module.enums.RiskControlTypeEnum;

import java.util.List;

/**
 * @author meteor
 */
public interface RiskControlRepository {
    /**
     * 查询通道配置
     * @param operationType /
     * @return /
     */
    List<RiskControl> findByOperationType(String operationType);

    /**
     * 查找一个风控配置
     * @param operationType /
     * @param riskControlType /
     * @return /
     */
    RiskControl findFirst(String operationType, RiskControlTypeEnum riskControlType);
}
