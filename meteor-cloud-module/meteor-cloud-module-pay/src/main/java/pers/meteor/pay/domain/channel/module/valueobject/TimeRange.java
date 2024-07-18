package pers.meteor.pay.domain.channel.module.valueobject;

import lombok.Data;
import pers.meteor.common.core.exception.ServiceException;

import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * 时间范围
 * @author meteor
 */
@Data
public class TimeRange {
    /**
     * 开始时间
     */
    private LocalTime startTime;
    /**
     * 截止时间
     */
    private LocalTime endTime;

    public TimeRange(LocalTime startTime, LocalTime endTime) {
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
