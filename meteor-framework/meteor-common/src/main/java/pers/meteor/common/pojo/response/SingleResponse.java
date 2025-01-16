package pers.meteor.common.pojo.response;

import lombok.Getter;
import lombok.Setter;
import pers.meteor.common.exception.ErrorCode;
import pers.meteor.common.exception.enums.GlobalErrorCode;

/**
 * @author 钟宗兵
 * @since 1.0
 **/
@Setter
@Getter
public class SingleResponse<T> extends Response {

    private static final long serialVersionUID = 1L;

    private T data;

    public static SingleResponse<Void> success() {
        SingleResponse<Void> response = new SingleResponse<>();
        response.setCode(GlobalErrorCode.SUCCESS.getCode());
        response.setSuccess(true);
        return response;
    }

    public static SingleResponse<Void> error(int errCode, String errMessage) {
        SingleResponse<Void> response = new SingleResponse<>();
        response.setSuccess(false);
        response.setCode(errCode);
        response.setMessage(errMessage);
        return response;
    }

    public static SingleResponse<Void> error(ErrorCode errorCode) {
        SingleResponse<Void> response = new SingleResponse<>();
        response.setSuccess(false);
        response.setCode(errorCode.getCode());
        response.setMessage(errorCode.getMsg());
        return response;
    }

    public static <T> SingleResponse<T> success(T data) {
        SingleResponse<T> response = new SingleResponse<>();
        response.setSuccess(true);
        response.setCode(GlobalErrorCode.SUCCESS.getCode());
        response.setData(data);
        return response;
    }

    public static <T> SingleResponse<T> error(String message, T data) {
        SingleResponse<T> response = new SingleResponse<>();
        response.setSuccess(false);
        response.setData(data);
        response.setCode(GlobalErrorCode.INTERNAL_SERVER_ERROR.getCode());
        response.setMessage(message);
        return response;
    }

    public T getCheckedData() {
        checkError();
        return data;
    }
}
