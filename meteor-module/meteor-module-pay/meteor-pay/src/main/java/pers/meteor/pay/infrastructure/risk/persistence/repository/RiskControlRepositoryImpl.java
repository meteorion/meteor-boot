package pers.meteor.pay.infrastructure.risk.persistence.repository;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import pers.meteor.pay.domain.risk.module.RiskControl;
import pers.meteor.pay.domain.risk.module.RiskControlParam;
import pers.meteor.pay.domain.risk.module.enums.RiskControlTypeEnum;
import pers.meteor.pay.domain.risk.repository.RiskControlRepository;
import pers.meteor.pay.infrastructure.risk.persistence.mapper.RiskControlMapper;
import pers.meteor.pay.infrastructure.risk.persistence.mapper.RiskControlParamMapper;
import pers.meteor.pay.infrastructure.risk.persistence.mapstruct.RiskControlMapStruct;
import pers.meteor.pay.infrastructure.risk.persistence.mapstruct.RiskControlParamMapStruct;
import pers.meteor.pay.infrastructure.risk.persistence.po.RiskControlParamPo;
import pers.meteor.pay.infrastructure.risk.persistence.po.RiskControlPo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author meteor
 */
@Repository
public class RiskControlRepositoryImpl implements RiskControlRepository {
    private final Logger logger = LoggerFactory.getLogger(RiskControlRepositoryImpl.class);
    private final RiskControlMapStruct riskControlConverter;
    private final RiskControlParamMapStruct riskControlParamConverter;
    private final RiskControlMapper riskControlMapper;
    private final RiskControlParamMapper riskControlParamMapper;

    public RiskControlRepositoryImpl(RiskControlMapper riskControlMapper,
                                     RiskControlParamMapper riskControlParamMapper) {
        this.riskControlMapper = riskControlMapper;
        this.riskControlParamMapper = riskControlParamMapper;
        riskControlConverter = RiskControlMapStruct.INSTANCE;
        riskControlParamConverter = RiskControlParamMapStruct.INSTANCE;
    }

    @Override
    public List<RiskControl> findByOperationType(String operationType) {
        if (ObjectUtils.isEmpty(operationType)) {
            logger.info("operationType is null");
            return new ArrayList<>();
        }

        LambdaQueryWrapper<RiskControlPo> queryWrapper = Wrappers.lambdaQuery(RiskControlPo.class)
                .eq(RiskControlPo::getOperationType, operationType);

        List<RiskControlPo> riskControlPos = riskControlMapper.selectList(queryWrapper);

        return riskControlPos.stream()
                .map(item -> {
                    RiskControl riskControlConfig = riskControlConverter.toEntity(item);
                    List<RiskControlParam> paramByGroupId = findParamByGroupId(riskControlConfig.getParamGroupIds());
                    riskControlConfig.setRiskControlParams(paramByGroupId);
                    return riskControlConfig;
                })
                .collect(Collectors.toList());
    }

    @Override
    public RiskControl findFirst(String operationType, RiskControlTypeEnum riskControlType) {
        if (ObjectUtils.isEmpty(operationType) || riskControlType == null) {
            logger.info("operationType or riskControlType is null");
            return null;
        }
        LambdaQueryWrapper<RiskControlPo> queryWrapper = Wrappers.lambdaQuery(RiskControlPo.class)
                .eq(RiskControlPo::getOperationType, operationType)
                .eq(RiskControlPo::getRiskControlType, riskControlType.name());

        return riskControlMapper.selectList(queryWrapper)
                .stream()
                .findFirst()
                .map(item -> {
                    RiskControl riskControlConfig = riskControlConverter.toEntity(item);
                    List<RiskControlParam> paramByGroupId = findParamByGroupId(riskControlConfig.getParamGroupIds());
                    riskControlConfig.setRiskControlParams(paramByGroupId);
                    return riskControlConfig;
                })
                .orElse(null);
    }

    private List<RiskControlParam> findParamByGroupId(List<String> groupIds) {
        if (CollectionUtils.isEmpty(groupIds)) {
            logger.warn("groupIds is empty");
            return new ArrayList<>();
        }
        return groupIds.stream()
                .map(this::findParamByGroupId)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    private List<RiskControlParam> findParamByGroupId(String groupId) {
        if (groupId == null) {
            logger.warn("groupId is null");
            return new ArrayList<>();
        }

        LambdaQueryWrapper<RiskControlParamPo> queryWrapper = Wrappers.lambdaQuery(RiskControlParamPo.class)
                .eq(RiskControlParamPo::getGroupId, groupId);

        List<RiskControlParamPo> riskControlParamPos = riskControlParamMapper.selectList(queryWrapper);
        return riskControlParamPos.stream()
                .map(riskControlParamConverter::toEntity)
                .collect(Collectors.toList());
    }
}
