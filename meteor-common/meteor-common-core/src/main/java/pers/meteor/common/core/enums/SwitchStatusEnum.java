package pers.meteor.common.core.enums;

import lombok.Getter;

/**
 * @author meteor
 */
@Getter
public enum SwitchStatusEnum {
    /**
     * 关闭
     */
    CLOSE(0),
    /**
     * 开启
     */
    OPEN(1)
    ;
    private final int code;

    SwitchStatusEnum(int code) {
        this.code = code;
    }

    public static SwitchStatusEnum ofCode(Integer code) {
        if (code == null) {
            return null;
        }
        for (SwitchStatusEnum switchStatus : values()) {
            if (switchStatus.code == code) {
                return switchStatus;
            }
        }
        return null;
    }
}
