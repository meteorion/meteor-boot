package pers.meteor.pay.infrastructure.risk.persistence.mapstruct;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import pers.meteor.pay.domain.risk.module.RiskControl;
import pers.meteor.pay.infrastructure.risk.persistence.po.RiskControlPo;

/**
 * @author meteor
 */
@Mapper
public interface RiskControlMapStruct {
    RiskControlMapStruct INSTANCE = Mappers.getMapper(RiskControlMapStruct.class);
    /**
     * 转实体
     * @param riskControlPo /
     * @return /
     */

    RiskControl toEntity(final RiskControlPo riskControlPo);
}
