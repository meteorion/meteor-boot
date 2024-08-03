package pers.meteor.pay.domain.order.module.valueobject;

import lombok.Data;
import pers.meteor.pay.domain.order.module.enums.RefundStatusEnum;
import pers.meteor.pay.domain.order.module.enums.TransferStatusEnum;
import pers.meteor.pay.infrastructure.channel.exception.PayException;

import java.time.LocalDateTime;

/**
 * 退款响应
 *
 * @author meteor
 */
@Data
public class TransferResponse {
    /**
     * 转账状态
     *
     * 关联 {@link  TransferStatusEnum#getStatus()}
     */
    private Integer status;

    /**
     * 外部转账单号
     *
     */
    private String outTransferNo;

    /**
     * 支付渠道编号
     */
    private String channelOrderNo;

    /**
     * 支付成功时间
     */
    private LocalDateTime successTime;

    /**
     * 原始的返回结果
     */
    private Object rawData;

    /**
     * 调用渠道的错误码
     */
    private String channelErrorCode;
    /**
     * 调用渠道报错时，错误信息
     */
    private String channelErrorMsg;

    /**
     * 创建【WAITING】状态的转账返回
     */
    public static TransferResponse waitingOf(String channelOrderNo,
                                               String outTransferNo, Object rawData) {
        TransferResponse respDTO = new TransferResponse();
        respDTO.status = TransferStatusEnum.WAITING.getStatus();
        respDTO.channelOrderNo = channelOrderNo;
        respDTO.outTransferNo = outTransferNo;
        respDTO.rawData = rawData;
        return respDTO;
    }

    /**
     * 创建【CLOSED】状态的转账返回
     */
    public static TransferResponse closedOf(String channelErrorCode, String channelErrorMsg,
                                              String outTransferNo, Object rawData) {
        TransferResponse respDTO = new TransferResponse();
        respDTO.status = TransferStatusEnum.CLOSED.getStatus();
        respDTO.channelErrorCode = channelErrorCode;
        respDTO.channelErrorMsg = channelErrorMsg;
        // 相对通用的字段
        respDTO.outTransferNo = outTransferNo;
        respDTO.rawData = rawData;
        return respDTO;
    }

    /**
     * 创建【SUCCESS】状态的转账返回
     */
    public static TransferResponse successOf(String channelTransferNo, LocalDateTime successTime,
                                               String outTransferNo, Object rawData) {
        TransferResponse respDTO = new TransferResponse();
        respDTO.status = TransferStatusEnum.SUCCESS.getStatus();
        respDTO.channelOrderNo = channelTransferNo;
        respDTO.successTime = successTime;
        // 相对通用的字段
        respDTO.outTransferNo = outTransferNo;
        respDTO.rawData = rawData;
        return respDTO;
    }
}
