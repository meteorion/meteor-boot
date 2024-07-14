package pers.meteor.pay.infrastructure.channel.persistence.po;

import lombok.Data;

/**
 * @author meteor
 */
@Data
public class PayChannelPo {
    private Long payChannelId;
    private String name;
    private String code;
    private Integer status;
}
