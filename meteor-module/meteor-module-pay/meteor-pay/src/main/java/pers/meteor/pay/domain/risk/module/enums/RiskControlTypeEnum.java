package pers.meteor.pay.domain.risk.module.enums;

import lombok.Getter;
import pers.meteor.pay.application.risk.RiskController;
import pers.meteor.pay.application.risk.impl.trade.AddressRiskController;

/**
 * @author meteor
 */
@Getter
public enum RiskControlTypeEnum {
    /**
     * 交易地区
     */
    TRADE_ADDRESS(AddressRiskController.class),
    ;

    private final Class<? extends RiskController> serviceClass;

    RiskControlTypeEnum(Class<? extends RiskController> serviceClass) {
        this.serviceClass = serviceClass;
    }
}
