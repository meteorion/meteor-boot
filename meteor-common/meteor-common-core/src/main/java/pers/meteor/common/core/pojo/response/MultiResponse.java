package pers.meteor.common.core.pojo.response;


import lombok.Getter;
import lombok.Setter;
import pers.meteor.common.core.exception.GlobalErrorCode;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * @author 钟宗兵
 * @since 1.0
 **/
@Setter
@Getter
public class MultiResponse<T> extends Response {

    private static final long serialVersionUID = 1L;

    private Collection<T> data;

    public List<T> getData() {
        if (null == data) {
            return Collections.emptyList();
        }
        if (data instanceof List) {
            return (List<T>) data;
        }
        return new ArrayList<>(data);
    }

    public boolean isEmpty() {
        return data == null || data.isEmpty();
    }

    public static MultiResponse<Void> success() {
        MultiResponse<Void> response = new MultiResponse<>();
        response.setSuccess(true);
        response.setCode(GlobalErrorCode.SUCCESS.getCode());
        return response;
    }

    public static MultiResponse<Void> error(int errCode, String errMessage) {
        MultiResponse<Void> response = new MultiResponse<>();
        response.setSuccess(false);
        response.setCode(errCode);
        response.setMessage(errMessage);
        return response;
    }

    public static <T> MultiResponse<T> success(Collection<T> data) {
        MultiResponse<T> response = new MultiResponse<>();
        response.setSuccess(true);
        response.setCode(GlobalErrorCode.SUCCESS.getCode());
        response.setData(data);
        return response;
    }

}
