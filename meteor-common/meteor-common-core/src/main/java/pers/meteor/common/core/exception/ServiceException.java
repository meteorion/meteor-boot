package pers.meteor.common.core.exception;

import lombok.Getter;
import lombok.Setter;

import java.text.MessageFormat;

/**
 * 业务异常
 *
 * @author ruoyi
 */
@Setter
@Getter
public final class ServiceException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    /**
     * 错误码
     */
    private Integer code;

    /**
     * 错误提示
     */
    private String message;

    /**
     * 错误明细，内部调试错误
     * <p>
     */
    private String detailMessage;

    /**
     * 空构造方法，避免反序列化问题
     */
    public ServiceException() {
        this.code = GlobalErrorCode.UNKNOWN.getCode();
        this.message = GlobalErrorCode.UNKNOWN.getMsg();
    }

    public ServiceException(String message, Object... args) {
        this.message = MessageFormat.format(message, args);
        this.code = GlobalErrorCode.UNKNOWN.getCode();
    }

    public ServiceException(Integer code, String message, Object... args) {
        this.message = MessageFormat.format(message, args);
        this.code = code;
    }
}
