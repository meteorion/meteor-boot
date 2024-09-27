package pers.meteor.pay.application.order.impl;

import org.springframework.stereotype.Service;
import pers.meteor.pay.application.channel.PayClient;
import pers.meteor.pay.application.order.RiskControlAppService;
import pers.meteor.pay.domain.order.module.PayOrder;

/**
 * @author 钟宗兵
 * @since 1.0.0
 */
@Service
public class RiskControlAppServiceImpl implements RiskControlAppService {

    @Override
    public void doRiskControl(PayClient payClient, PayOrder payOrder) {

    }
}
