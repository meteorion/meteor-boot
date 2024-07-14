package pers.meteor.pay.domain.channel.module.valueobject;

import lombok.Data;

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
}
