package pers.meteor.pay.domain.channel.module.enums;

import cn.hutool.core.util.ArrayUtil;
import lombok.Getter;

/**
 * @author meteor
 */
@Getter
public enum SignTypeEnum {
    MD5("md5")
    ;
    private final String code;

    SignTypeEnum(String code) {
        this.code = code;
    }

    public static SignTypeEnum getByCode(String code) {
        return ArrayUtil.firstMatch(o -> o.getCode().equals(code), values());
    }
}
