package pers.meteor.pay.domain.channel.acl;

import pers.meteor.pay.domain.channel.module.enums.RateTypeEnum;
import pers.meteor.pay.domain.channel.module.valueobject.ChannelConfig;
import pers.meteor.pay.domain.channel.module.valueobject.SystemChannelConfig;

import java.util.EnumMap;

/**
 * @author meteor
 */
public interface RemotePayConfigAdapter {

    /**
     * 获取系统通道配置
     * @return /
     */
    SystemChannelConfig getSystemChannelConfig();
    /**
     * 获取默认费率配置
     *
     * @return /
     */
    EnumMap<RateTypeEnum, ChannelConfig> getDefaultRates();
}
