package pers.meteor.pay.domain.order.module.enums;

import lombok.Getter;

/**
 * 币种类型
 * @author meteor
 */
@Getter
public enum CurrencyTypeEnum {
    CNY("CNY", "人民币"),
    USD("USD", "美元"),
    HKD("HKD", "港币"),
    EUR("EUR", "欧元"),
    JPY("JPY", "日元"),
    GBP("GBP", "英镑"),
    AUD("AUD", "澳元"),
    CAD("CAD", "加元"),
    CHF("CHF", "瑞士法郎"),
    NZD("NZD", "新西兰元"),
    SGD("SGD", "新加坡元"),
    THB("THB", "泰铢"),
    ;

    private final String code;
    private final String desc;

    CurrencyTypeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static CurrencyTypeEnum getByCode(String code) {
        for (CurrencyTypeEnum currencyTypeEnum : CurrencyTypeEnum.values()) {
            if (currencyTypeEnum.getCode().equals(code)) {
                return currencyTypeEnum;
            }
        }
        return null;
    }

    public static CurrencyTypeEnum checkAndGetByCode(String code) {
        CurrencyTypeEnum currencyType = getByCode(code);
        if (currencyType == null) {
            throw new IllegalArgumentException("CurrencyTypeEnum not exist, code: " + code);
        }

        return currencyType;
    }
}
