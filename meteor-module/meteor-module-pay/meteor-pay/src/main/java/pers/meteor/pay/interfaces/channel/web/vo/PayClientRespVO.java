package pers.meteor.pay.interfaces.channel.web.vo;

import lombok.Data;

/**
 * @author meteor
 */
@Data
public class PayClientRespVO {
    /**
     * 主键
     */
    private Long clientId;
    /**
     * 支付通道id
     */
    private Long channelId;
    /**
     * 支付通道类型，{@link  pers.meteor.pay.domain.channel.module.enums.PayChannelEnum}
     */
    private String channelType;
    /**
     * 服务地址
     */
    private String serviceUrl;
    /**
     * 扩展参数
     */
    private String metadata;
}
