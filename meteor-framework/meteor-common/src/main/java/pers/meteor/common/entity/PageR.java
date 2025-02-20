package pers.meteor.common.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.cloud.client.loadbalancer.Response;
import pers.meteor.common.exception.GlobalErrorCode;

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
public class PageR<T> extends R<Collection<T>> {

    private static final long serialVersionUID = 1L;

    private long totalCount = 0;

    private long pageSize = 1;

    private long pageIndex = 1;

    public long getPageSize() {
        return Math.max(pageSize, 1);
    }

    public void setPageSize(long pageSize) {
        this.pageSize = Math.max(pageSize, 1);
    }

    public long getPageIndex() {
        return Math.max(pageIndex, 1);
    }

    public void setPageIndex(long pageIndex) {
        this.pageIndex = Math.max(pageIndex, 1);
    }

    public List<T> getData() {
        if (null == data) {
            return Collections.emptyList();
        }
        if (data instanceof List) {
            return (List<T>) data;
        }
        return new ArrayList<>(data);
    }

    public long getTotalPages() {
        return this.totalCount % this.pageSize == 0 ? this.totalCount
                / this.pageSize : (this.totalCount / this.pageSize) + 1;
    }

    public boolean isEmpty() {
        return data == null || data.isEmpty();
    }

    public boolean isNotEmpty() {
        return !isEmpty();
    }


    public static PageR<Void> success() {
        PageR<Void> response = new PageR<>();
        response.setSuccess(true);
        response.setCode(GlobalErrorCode.SUCCESS.getCode());
        return response;
    }

    public static PageR<Void> error(int errCode, String errMessage) {
        PageR<Void> response = new PageR<>();
        response.setSuccess(false);
        response.setCode(errCode);
        response.setMsg(errMessage);
        return response;
    }

    public static <T> PageR<T> success(long pageSize, long pageIndex) {
        PageR<T> response = new PageR<>();
        response.setSuccess(true);
        response.setCode(GlobalErrorCode.SUCCESS.getCode());
        response.setData(Collections.emptyList());
        response.setTotalCount(0);
        response.setPageSize(pageSize);
        response.setPageIndex(pageIndex);
        return response;
    }

    public static <T> PageR<T> success(Collection<T> data, long totalCount, long pageSize, long pageIndex) {
        PageR<T> response = new PageR<>();
        response.setSuccess(true);
        response.setCode(GlobalErrorCode.SUCCESS.getCode());
        response.setData(data);
        response.setTotalCount(totalCount);
        response.setPageSize(pageSize);
        response.setPageIndex(pageIndex);
        return response;
    }

}
