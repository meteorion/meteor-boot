package pers.meteor.pay.interfaces.channel.web.vo;

import lombok.Data;

/**
 * @author meteor
 */
@Data
public class PayClientConfigVO {
    /**
     * 主键
     */
    private Long channelConfigId;
    /**
     * 支付通道id
     */
    private Long payChannelId;
    /**
     * 支付通道类型，{@link  pers.meteor.pay.domain.channel.module.enums.PayChannelEnum}
     */
    private String channelType;
    /**
     * 服务地址
     */
    private String serviceUrl;
    /**
     * 运用id
     */
    private String appId;

    /**
     * 扩展参数
     */
    private String metadata;
}
