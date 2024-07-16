package pers.meteor.pay.interfaces.channel.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author meteor
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class PayChannelRespVO extends PayChannelBaseVO {
    /**
     * 通道id
     */
    private Long payChannelId;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    /**
     * 通道配置
     */
    private List<PayChannelConfigRespVO> configs;
}
