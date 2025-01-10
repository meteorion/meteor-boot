package pers.meteor.pay.domain.risk.module.enums;

import lombok.Getter;

/**
 * @author meteor
 */
@Getter
public enum RuleTypeEnum {
    REGION("地区风控"),
    IP("ip风控")
    ;

    private final String name;

    RuleTypeEnum(String name) {
        this.name = name;
    }
}
