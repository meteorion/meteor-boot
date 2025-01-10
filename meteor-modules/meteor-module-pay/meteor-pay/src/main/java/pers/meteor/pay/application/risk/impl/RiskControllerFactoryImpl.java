package pers.meteor.pay.application.risk.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.util.ReflectionUtils;
import pers.meteor.common.exception.ServiceException;
import pers.meteor.pay.application.risk.RiskController;
import pers.meteor.pay.application.risk.RiskControllerFactory;
import pers.meteor.pay.domain.risk.module.RiskControl;
import pers.meteor.pay.domain.risk.module.RiskControlParam;
import pers.meteor.pay.domain.risk.module.enums.RiskControlTypeEnum;

import java.lang.reflect.Constructor;
import java.util.*;

/**
 * @author meteor
 */
public class RiskControllerFactoryImpl implements RiskControllerFactory {
    private static final Logger log = LoggerFactory.getLogger(RiskControllerFactoryImpl.class);
    // 风控服务
    private final Map<String, EnumMap<RiskControlTypeEnum, RiskController>> riskControlServices = new HashMap<>();

    @Override
    public void clear() {
        riskControlServices.clear();
    }

    @Override
    public void registerRiskControl(RiskControl riskControl) {
        String operationType = riskControl.getOperationType();
        RiskControlTypeEnum riskControlType = riskControl.getRiskControlType();
        List<RiskControlParam> riskControlParams = riskControl.getRiskControlParams();

        EnumMap<RiskControlTypeEnum, RiskController> riskControllerEnumMap = riskControlServices.get(operationType);
        if (riskControllerEnumMap == null) {
            riskControllerEnumMap = new EnumMap<>(RiskControlTypeEnum.class);
        }

        Constructor<?> constructor = ReflectionUtils.findConstructor(riskControlType.getServiceClass(), riskControlType.name(), riskControlParams).orElse(null);
        if (constructor == null) {
            constructor = ReflectionUtils.findConstructor(riskControlType.getServiceClass(), riskControlParams).orElse(null);
            if (constructor == null) {
                throw new ServiceException("未获取到构造器");
            }
            try {
                RiskController riskController = (RiskController) constructor.newInstance(riskControlParams);
                riskControllerEnumMap.put(riskControlType, riskController);
            } catch (Exception e) {
                log.error("实例化失败", e);
                throw new ServiceException("风控服务实例化失败");
            }
        } else {
            try {
                RiskController riskController = (RiskController) constructor.newInstance(riskControlType.name(), riskControlParams);
                riskControllerEnumMap.put(riskControlType, riskController);
            } catch (Exception e) {
                log.error("实例化失败", e);
                throw new ServiceException("风控服务实例化失败");
            }
        }
        riskControlServices.put(operationType, riskControllerEnumMap);
    }

    @Override
    public RiskController getRiskControlService(String operationType, RiskControlTypeEnum riskControlType) {
        EnumMap<RiskControlTypeEnum, RiskController> riskControllerEnumMap = riskControlServices.get(operationType);
        if (riskControllerEnumMap == null) {
            throw new ServiceException("风控服务未配置");
        }
        RiskController riskController = riskControllerEnumMap.get(riskControlType);
        if (riskController == null) {
            throw new ServiceException("风控服务未实例化");
        }
        return riskController;
    }

    @Override
    public Collection<RiskController> getRiskControlService(String operationType) {
        EnumMap<RiskControlTypeEnum, RiskController> riskControllerEnumMap = riskControlServices.get(operationType);
        if (riskControllerEnumMap == null) {
            return new ArrayList<>();
        }
        return riskControllerEnumMap.values();
    }
}
