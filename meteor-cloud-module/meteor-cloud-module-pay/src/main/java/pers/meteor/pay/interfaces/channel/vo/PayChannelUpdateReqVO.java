package pers.meteor.pay.interfaces.channel.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author meteor
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class PayChannelUpdateReqVO extends PayChannelBaseVO {
    /**
     * 通道id
     */
    private Long payChannelId;
    /**
     * 通道配置
     */
    private List<PayChannelConfigRespVO> configs;
}
