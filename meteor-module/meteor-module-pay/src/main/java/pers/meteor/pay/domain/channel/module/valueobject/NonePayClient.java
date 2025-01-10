package pers.meteor.pay.domain.channel.module.valueobject;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pers.meteor.pay.domain.channel.module.PayClient;

/**
 * @author meteor
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class NonePayClient extends PayClient {
    /**
     * 配置名称
     */
    private String name;

    public NonePayClient() {
        this.name = "none-config";
    }
}
