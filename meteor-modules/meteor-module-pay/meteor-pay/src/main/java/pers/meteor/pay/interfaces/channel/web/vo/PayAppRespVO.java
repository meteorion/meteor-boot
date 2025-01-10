package pers.meteor.pay.interfaces.channel.web.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author meteor
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class PayAppRespVO extends PayAppBaseVO {
    /**
     * 通道id
     */
    private Long appId;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    /**
     * 通道配置
     */
    private List<PayChannelRespVO> payChannels;
}
