package pers.meteor.pay.infrastructure.channel.persistence.po;

import lombok.Data;

/**
 * @author meteor
 */
@Data
public class PayClientConfigPo {
    private Long id;
    private Long payChannelId;
    /**
     * 支付通道类型，{@link  pers.meteor.pay.domain.channel.module.enums.PayChannelEnum}
     */
    private String channelType;
}
