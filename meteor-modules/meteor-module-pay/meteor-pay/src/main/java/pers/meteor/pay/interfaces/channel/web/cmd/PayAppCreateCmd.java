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
public class PayAppCreateCmd extends PayAppBaseVO {
    /**
     * 通道配置
     */
    private List<PayChannelCreateCmd> configs;
}
