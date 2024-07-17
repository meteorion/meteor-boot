package pers.meteor.pay.interfaces.channel.web.vo;

import lombok.Data;

/**
 * @author meteor
 */
@Data
public class PayChannelSimpleRespVO {
    /**
     * 通道id
     */
    private Long payChannelId;
    /**
     * 通道名称
     */
    private String name;
    /**
     * 通道代号
     */
    private String code;
    /**
     * 通道状态
     */
    private Integer status;
}
