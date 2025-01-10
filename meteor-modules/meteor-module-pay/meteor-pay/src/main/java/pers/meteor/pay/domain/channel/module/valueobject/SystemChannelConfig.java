package pers.meteor.pay.domain.channel.module.valueobject;

import lombok.Data;

/**
 * 通道系统配置
 *
 * @author meteor
 */
@Data
public class SystemChannelConfig {
    /**
     * 唯一系统代号
     */
    private boolean uniqueCode;
}
