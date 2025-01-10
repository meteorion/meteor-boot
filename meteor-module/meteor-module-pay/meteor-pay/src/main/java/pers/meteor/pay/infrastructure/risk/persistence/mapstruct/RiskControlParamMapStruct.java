package pers.meteor.pay.infrastructure.risk.persistence.mapstruct;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import pers.meteor.pay.domain.risk.module.RiskControlParam;
import pers.meteor.pay.infrastructure.risk.persistence.po.RiskControlParamPo;

/**
 * @author meteor
 */
@Mapper
public interface RiskControlParamMapStruct {
    RiskControlParamMapStruct INSTANCE = Mappers.getMapper(RiskControlParamMapStruct.class);
    /**
     * 转实体
     * @param riskControlParamPo /
     * @return /
     */
    RiskControlParam toEntity(final RiskControlParamPo riskControlParamPo);
}
