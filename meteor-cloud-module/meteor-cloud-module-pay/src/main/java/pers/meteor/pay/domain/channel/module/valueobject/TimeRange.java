package pers.meteor.pay.domain.channel.module.valueobject;

import lombok.Data;
import pers.meteor.common.core.exception.ServiceException;

import java.time.LocalDateTime;

/**
 * 时间范围
 * @author meteor
 */
@Data
public class TimeRange {
    /**
     * 开始时间
     */
    private LocalDateTime startTime;
    /**
     * 截止时间
     */
    private LocalDateTime endTime;

    public TimeRange(LocalDateTime startTime, LocalDateTime endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.checkRange();
    }

    public void checkRange() {
        if (startTime == null) {
            throw new ServiceException("开始时间不能为空");
        }
        if (endTime == null) {
            throw new ServiceException("截止时间不能为空");
        }
        if (startTime.isAfter(endTime)) {
            throw new ServiceException("开始时间不能早于截止时间");
        }
    }
}
