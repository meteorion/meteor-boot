package pers.meteor.pay.application.risk.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import pers.meteor.common.exception.ServiceException;
import pers.meteor.pay.application.risk.RiskControlAppService;
import pers.meteor.pay.application.risk.RiskController;
import pers.meteor.pay.application.risk.RiskControllerFactory;
import pers.meteor.pay.cmd.PayOrderCreateCmd;
import pers.meteor.pay.cmd.PayOrderSubmitCmd;
import pers.meteor.pay.domain.risk.module.RiskControl;
import pers.meteor.pay.domain.risk.module.RiskControlResult;
import pers.meteor.pay.domain.risk.module.enums.OperationTypeEnum;
import pers.meteor.pay.domain.risk.module.enums.RiskControlTypeEnum;
import pers.meteor.pay.domain.risk.repository.RiskControlRepository;

import java.util.Collection;
import java.util.List;

/**
 * @author 钟宗兵
 * @since 1.0.0
 */
@Slf4j
@Service
public class RiskControlAppServiceImpl implements RiskControlAppService {
    private final RiskControllerFactory riskControlFactory = new RiskControllerFactoryImpl();
    private final RiskControlRepository riskControlRepository;

    public RiskControlAppServiceImpl(RiskControlRepository riskControlRepository) {
        this.riskControlRepository = riskControlRepository;
        refreshRiskControl();
    }

    @Scheduled(cron = "0 */2 * * * *")
    public void refreshRiskControl() {
        log.info("刷新风控配置");
        for (OperationTypeEnum operationType : OperationTypeEnum.values()) {
            this.refreshRiskControl(operationType.name());
        }
    }

    private void refreshRiskControl(String operationType) {
//        riskControlFactory.clear();
        List<RiskControl> riskControls = riskControlRepository.findByOperationType(operationType);
        for (RiskControl riskControl : riskControls) {
            if (Boolean.FALSE.equals(riskControl.getOpen())) {
                log.info("风控已关闭：{}-{}", operationType, riskControl.getRiskControlType());
                continue;
            }
            riskControlFactory.registerRiskControl(riskControl);
        }
    }

    public RiskControlResult doRiskChecking(OperationTypeEnum operationType, Object object, boolean refreshConfig) {
        if (refreshConfig) {
            refreshRiskControl(operationType.name());
        }
        Collection<RiskController> riskControllers = riskControlFactory.getRiskControlService(operationType.name());
        for (RiskController riskController : riskControllers) {
            RiskControlResult riskControlResult = riskController.doRiskChecking(object);
            if (!riskControlResult.isPass()) {
                return riskControlResult;
            }
        }
        return RiskControlResult.pass(null);
    }

    public void doRiskChecking(OperationTypeEnum operationType, List<RiskControlTypeEnum> controlTypes, Object object, boolean refreshConfig) {
        if (CollectionUtils.isEmpty(controlTypes)) {
            return;
        }
        if (refreshConfig) {
            refreshRiskControl(operationType.name());
        }
        for (RiskControlTypeEnum controlType : controlTypes) {
            RiskControlResult riskControlResult = doRiskChecking(operationType, controlType, object);
            if (!riskControlResult.isPass()) {
                throw new ServiceException(riskControlResult.getMessage());
            }
        }
    }

    public RiskControlResult doRiskChecking(OperationTypeEnum operationType, RiskControlTypeEnum controlType, Object object) {
        RiskController riskController = riskControlFactory.getRiskControlService(operationType.name(), controlType);
        return riskController.doRiskChecking(object);
    }

    @Override
    public void doOrderRiskControl(PayOrderCreateCmd payOrderCreateCmd) {
        log.info("下单风控");
    }

    @Override
    public void doPayRiskControl(PayOrderSubmitCmd payOrderSubmitCmd) {
        log.info("支付风控");
    }
}
