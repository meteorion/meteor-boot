package pers.meteor.pay.application.risk;

import pers.meteor.pay.cmd.PayOrderCreateCmd;
import pers.meteor.pay.cmd.PayOrderSubmitCmd;

/**
 * @author 钟宗兵
 * @since 1.0.0
 */
public interface RiskControlAppService {
    /**
     * 下单风控
     *
     * @param payOrderCreateCmd /
     */
    void doOrderRiskControl(PayOrderCreateCmd payOrderCreateCmd);

    /**
     * 执行风控管理
     *
     * @param payOrderSubmitCmd /
     */
    void doPayRiskControl(PayOrderSubmitCmd payOrderSubmitCmd);
}
