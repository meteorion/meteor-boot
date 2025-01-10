package pers.meteor.pay.domain.order.module.valueobject;

import lombok.Data;
import pers.meteor.pay.domain.order.module.enums.RefundStatusEnum;
import pers.meteor.pay.infrastructure.channel.exception.PayException;

import java.time.LocalDateTime;

/**
 * 退款响应
 *
 * @author meteor
 */
@Data
public class RefundResponse {
    /**
     * 退款状态
     * 枚举 {@link RefundStatusEnum}
     */
    private Integer status;

    /**
     * 外部退款号
     * 对应 PayRefundDO 的 no 字段
     */
    private String refundOrderNo;

    /**
     * 渠道退款单号
     * 对应 PayRefundDO.channelRefundNo 字段
     */
    private String channelRefundNo;

    /**
     * 退款成功时间
     */
    private LocalDateTime refundTime;

    /**
     * 原始的异步通知结果
     */
    private Object rawData;

    /**
     * 调用渠道的错误码
     * 注意：这里返回的是业务异常，而是不系统异常。
     * 如果是系统异常，则会抛出 {@link PayException}
     */
    private String channelErrorCode;
    /**
     * 调用渠道报错时，错误信息
     */
    private String channelErrorMsg;

    private RefundResponse() {
    }

    /**
     * 创建【WAITING】状态的退款返回
     */
    public static RefundResponse waitingOf(String channelRefundNo,
                                             String refundOrderNo, Object rawData) {
        RefundResponse respDTO = new RefundResponse();
        respDTO.status = RefundStatusEnum.WAITING.getStatus();
        respDTO.channelRefundNo = channelRefundNo;
        // 相对通用的字段
        respDTO.refundOrderNo = refundOrderNo;
        respDTO.rawData = rawData;
        return respDTO;
    }

    /**
     * 创建【SUCCESS】状态的退款返回
     */
    public static RefundResponse successOf(String channelRefundNo, LocalDateTime successTime,
                                             String refundOrderNo, Object rawData) {
        RefundResponse respDTO = new RefundResponse();
        respDTO.status = RefundStatusEnum.SUCCESS.getStatus();
        respDTO.channelRefundNo = channelRefundNo;
        respDTO.refundTime = successTime;
        // 相对通用的字段
        respDTO.refundOrderNo = refundOrderNo;
        respDTO.rawData = rawData;
        return respDTO;
    }

    /**
     * 创建【FAILURE】状态的退款返回
     */
    public static RefundResponse failureOf(String refundOrderNo, Object rawData) {
        return failureOf(null, null,
                refundOrderNo, rawData);
    }

    /**
     * 创建【FAILURE】状态的退款返回
     */
    public static RefundResponse failureOf(String channelErrorCode, String channelErrorMsg,
                                             String refundOrderNo, Object rawData) {
        RefundResponse respDTO = new RefundResponse();
        respDTO.status = RefundStatusEnum.FAILURE.getStatus();
        respDTO.channelErrorCode = channelErrorCode;
        respDTO.channelErrorMsg = channelErrorMsg;
        // 相对通用的字段
        respDTO.refundOrderNo = refundOrderNo;
        respDTO.rawData = rawData;
        return respDTO;
    }
}
