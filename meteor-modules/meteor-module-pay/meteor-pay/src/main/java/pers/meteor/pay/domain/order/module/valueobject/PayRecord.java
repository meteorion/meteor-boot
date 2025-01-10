package pers.meteor.pay.domain.order.module.valueobject;

import lombok.Data;

import java.util.Map;

/**
 * 支付记录
 * @author meteor
 */
@Data
public class PayRecord {

    /**
     * 订单拓展编号，数据库自增
     */
    private Long id;
    /**
     * 外部订单号，根据规则生成
     */
    private String orderNo;
    /**
     * 订单号
     *
     */
    private Long orderId;
    /**
     * 渠道编号
     *
     */
    private Long channelId;
    /**
     * 渠道编码
     */
    private String channelCode;
    /**
     * 用户 IP
     */
    private String userIp;
    /**
     * 支付状态
     *
     */
    private boolean success;
    /**
     * 支付渠道的额外参数
     */
    private Map<String, String> channelExtras;

    /**
     * 调用渠道的错误码
     */
    private String channelErrorCode;
    /**
     * 调用渠道报错时，错误信息
     */
    private String channelErrorMsg;

    /**
     * 支付渠道的同步/异步通知的内容
     *
     */
    private String channelNotifyData;
}
