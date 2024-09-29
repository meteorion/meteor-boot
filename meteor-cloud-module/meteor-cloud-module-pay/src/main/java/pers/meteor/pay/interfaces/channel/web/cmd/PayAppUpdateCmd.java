package pers.meteor.pay.interfaces.channel.web.cmd;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pers.meteor.pay.interfaces.channel.web.vo.PayAppBaseVO;

import java.util.List;

/**
 * @author meteor
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class PayAppUpdateCmd extends PayAppBaseVO {
    /**
     * 通道id
     */
    private Long payChannelId;
    /**
     * 通道配置
     */
    private List<PayChannelCreateCmd> configs;
}
