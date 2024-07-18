package pers.meteor.pay.interfaces.channel.web.vo;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * @author meteor
 */
@Data
public class PayChannelConfigReqVO {
    /**
     * 通道配置id
     */
    private Long channelConfigId;
    /**
     * 通道id
     */
    @NotNull(message = "渠道id不能为空")
    private Long payChannelId;
    /**
     * 支付通道类型，{@link  pers.meteor.pay.domain.channel.module.enums.PayChannelEnum}
     */
    @NotBlank(message = "支付通道类型不能为空")
    private String channelType;
    /**
     * 通道状态
     */
    private Integer status;

    /**
     * 成本费率（我方）
     */
    @NotNull(message = "成本费率不能为空")
    private BigDecimal costRate;
    /**
     * 成本手续费（我方）
     */
    private int costFee;
    /**
     * 最大费率(通道)
     */
    private BigDecimal maxRate;
    /**
     * 最低费率(通道)
     */
    private BigDecimal minRate;
    /**
     * 最大手续费(通道)
     */
    private int maxFee;
    /**
     * 最低手续费(通道)
     */
    private int minFee;

    /**
     * 日订单限额
     */
    private int dailyOrderLimit;
    /**
     * 日限额
     */
    private int dailyLimit;
    /**
     * 单笔最低金额
     */
    private int singleMinLimit;
    /**
     * 单笔最大限额
     */
    private int singleMaxLimit;

    /**
     * 开始时间
     */
    @NotNull(message = "渠道id不能为空")
    @DateTimeFormat(pattern = "HH:mm:ss")
    private LocalTime startTime;
    /**
     * 截止时间
     */
    @NotNull(message = "渠道id不能为空")
    @DateTimeFormat(pattern = "HH:mm:ss")
    private LocalTime endTime;
}
