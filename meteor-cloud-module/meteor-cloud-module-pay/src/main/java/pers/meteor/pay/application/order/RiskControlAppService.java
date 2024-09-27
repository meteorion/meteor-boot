package pers.meteor.pay.application.order;

import pers.meteor.pay.application.channel.PayClient;
import pers.meteor.pay.domain.order.module.PayOrder;

/**
 * @author 钟宗兵
 * @since 1.0.0
 */
public interface RiskControlAppService {
    /**
     * 执行风控管理
     *
     * @param payClient /
     * @param payOrder /
     */
    void doRiskControl(PayClient payClient, PayOrder payOrder);
}
