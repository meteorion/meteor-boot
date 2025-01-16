package pers.meteor.common.pojo.response;

import lombok.Getter;
import lombok.Setter;
import pers.meteor.common.exception.ErrorCode;
import pers.meteor.common.exception.enums.GlobalErrorCode;
import pers.meteor.common.exception.ServiceException;
import pers.meteor.common.pojo.command.Dto;

import java.text.MessageFormat;

/**
 * @author 钟宗兵
 * @since 1.0
 **/
@Setter
@Getter
public class Response extends Dto {

    private static final long serialVersionUID = 1L;

    private boolean success;

    private int code;

    private String message;

    @Override
    public String toString() {
        return "Response [success=" + success + ", code=" + code + ", message=" + message + "]";
    }

    public void checkError() {
        if (success) {
            return;
        }
        throw new ServiceException(message, code);
    }

    public static Response success() {
        Response response = new Response();
        response.setCode(GlobalErrorCode.SUCCESS.getCode());
        response.setSuccess(true);
        return response;
    }

    public static Response error(ServiceException serviceException) {
        Response response = new Response();
        response.setSuccess(false);
        response.setCode(serviceException.getCode());
        response.setMessage(serviceException.getMessage());
        return response;
    }

    public static Response error(ErrorCode errorCode) {
        Response response = new Response();
        response.setSuccess(false);
        response.setCode(errorCode.getCode());
        response.setMessage(errorCode.getMsg());
        return response;
    }

    public static Response error(ErrorCode errorCode, Object... message) {
        SingleResponse<Void> response = new SingleResponse<>();
        response.setSuccess(false);
        response.setCode(errorCode.getCode());
        response.setMessage(MessageFormat.format(errorCode.getMsg(), message));
        return response;
    }

    public static Response error(int code, String message) {
        Response response = new Response();
        response.setSuccess(false);
        response.setCode(code);
        response.setMessage(message);
        return response;
    }

    public static Response error(String message) {
        Response response = new Response();
        response.setSuccess(false);
        response.setCode(GlobalErrorCode.INTERNAL_SERVER_ERROR.getCode());
        response.setMessage(message);
        return response;
    }

}
