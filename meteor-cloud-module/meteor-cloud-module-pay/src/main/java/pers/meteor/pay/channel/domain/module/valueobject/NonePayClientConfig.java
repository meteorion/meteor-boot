package pers.meteor.pay.channel.domain.module.valueobject;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pers.meteor.pay.channel.domain.module.PayClientConfig;

/**
 * @author meteor
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class NonePayClientConfig extends PayClientConfig {
    /**
     * 配置名称
     */
    private String name;

    public NonePayClientConfig() {
        this.name = "none-config";
    }
}
