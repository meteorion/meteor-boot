package pers.meteor.pay.interfaces.channel.web.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author meteor
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class PayChannelCreateReqVO extends PayChannelBaseVO {
    /**
     * 通道配置
     */
    private List<PayChannelConfigReqVO> configs;
}
