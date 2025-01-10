package pers.meteor.pay.domain.order.module.valueobject;

import lombok.Data;
import pers.meteor.pay.domain.order.module.enums.DisplayModeEnum;
import pers.meteor.pay.domain.order.module.enums.PayStatusEnum;
import pers.meteor.pay.infrastructure.channel.exception.PayException;

import java.time.LocalDateTime;

/**
 * @author meteor
 */
@Data
public class PayResponse {
    /**
     * 交易通道ID
     */
    private Long tradeChannelId;
    /**
     * 支付状态
     */
    private PayStatusEnum payStatus;
    /**
     * 支付订单号
     */
    private String orderNo;
    /**
     * 通道订单号
     */
    private String channelOrderNo;
    /**
     * 通道用户id
     */
    private String channelUserId;

    /**
     * 支付时间
     */
    private LocalDateTime payTime;

    /**
     * 原始同步/异步通知结果
     */
    private Object rawData;

    // ========== 主动发起支付时，会返回的字段 ==========

    /**
     * 展示模式
     * 枚举 {@link DisplayModeEnum} 类
     */
    private DisplayModeEnum displayMode;
    /**
     * 展示内容
     */
    private String displayContent;

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


    /**
     * 创建【WAITING】状态的订单返回
     */
    public static PayResponse waitingOf(DisplayModeEnum displayMode, String displayContent,
                                            String orderNo, Object rawData) {
        PayResponse payResponse = new PayResponse();
        payResponse.payStatus = PayStatusEnum.WAITING;
        payResponse.displayMode = displayMode;
        payResponse.displayContent = displayContent;
        // 相对通用的字段
        payResponse.orderNo = orderNo;
        payResponse.rawData = rawData;
        return payResponse;
    }

    /**
     * 创建【SUCCESS】状态的订单返回
     */
    public static PayResponse successOf(String channelOrderNo, String channelUserId, LocalDateTime successTime,
                                            String orderNo, Object rawData) {
        PayResponse payResponse = new PayResponse();
        payResponse.payStatus = PayStatusEnum.SUCCESS;
        payResponse.channelOrderNo = channelOrderNo;
        payResponse.channelUserId = channelUserId;
        payResponse.payTime = successTime;
        // 相对通用的字段
        payResponse.orderNo = orderNo;
        payResponse.rawData = rawData;
        return payResponse;
    }

    /**
     * 创建指定状态的订单返回，适合支付渠道回调时
     */
    public static PayResponse of(Integer status, String channelOrderNo, String channelUserId, LocalDateTime successTime,
                                     String orderNo, Object rawData) {
        PayResponse payResponse = new PayResponse();
        payResponse.payStatus = PayStatusEnum.of(status);
        payResponse.channelOrderNo = channelOrderNo;
        payResponse.channelUserId = channelUserId;
        payResponse.payTime = successTime;
        // 相对通用的字段
        payResponse.orderNo = orderNo;
        payResponse.rawData = rawData;
        return payResponse;
    }

    /**
     * 创建【CLOSED】状态的订单返回，适合调用支付渠道失败时
     */
    public static PayResponse closedOf(String channelErrorCode, String channelErrorMsg,
                                           String orderNo, Object rawData) {
        PayResponse payResponse = new PayResponse();
        payResponse.payStatus = PayStatusEnum.CLOSED;
        payResponse.channelErrorCode = channelErrorCode;
        payResponse.channelErrorMsg = channelErrorMsg;
        // 相对通用的字段
        payResponse.orderNo = orderNo;
        payResponse.rawData = rawData;
        return payResponse;
    }
}
