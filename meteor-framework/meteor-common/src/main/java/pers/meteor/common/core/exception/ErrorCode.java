package pers.meteor.common.core.exception;

import lombok.Getter;

/**
 * 错误码对象
 * 全局错误码，占用 [0, 999], 参见 {@link GlobalErrorCode}
 * 业务异常错误码，占用 [1 000 000 000, +∞)
 */
@Getter
public class ErrorCode {

    /**
     * 错误码
     */
    private final Integer code;
    /**
     * 错误提示
     */
    private final String msg;

    public ErrorCode(Integer code, String message) {
        this.code = code;
        this.msg = message;
    }

}
