package pers.meteor.pay.domain.channel.acl;

import pers.meteor.pay.domain.channel.module.valueobject.Rate;
import pers.meteor.pay.domain.channel.module.valueobject.SystemChannelConfig;

import java.util.List;

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
    List<Rate> getDefaultRates();
}
