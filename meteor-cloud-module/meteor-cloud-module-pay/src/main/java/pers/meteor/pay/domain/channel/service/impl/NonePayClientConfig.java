package pers.meteor.pay.domain.channel.service.impl;

import lombok.Data;
import pers.meteor.pay.domain.channel.service.PayClientConfig;

/**
 * @author meteor
 */
@Data
public class NonePayClientConfig implements PayClientConfig {
    /**
     * 配置名称
     */
    private String name;

    public NonePayClientConfig() {
        this.name = "none-config";
    }
}
