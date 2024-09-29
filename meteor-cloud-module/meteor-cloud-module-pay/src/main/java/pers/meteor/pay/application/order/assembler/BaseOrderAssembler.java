package pers.meteor.pay.application.order.assembler;

import pers.meteor.pay.domain.order.module.enums.CurrencyTypeEnum;

/**
 * @author meteor
 */
public interface BaseOrderAssembler {

    default CurrencyTypeEnum getCurrencyType(String currency) {
        return CurrencyTypeEnum.checkAndGetByCode(currency);
    }
}
