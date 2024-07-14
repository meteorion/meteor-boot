package pers.meteor.pay.domain.channel.module.valueobject;

import lombok.Data;

/**
 * 通道额度
 *
 * @author meteor
 */
@Data
public class ChannelQuota {
    /**
     * 日订单限额
     */
    private int dailyOrderLimit;
    /**
     * 日限额
     */
    private int dailyLimit;
    /**
     * 单笔最低金额
     */
    private int singleMinLimit;
    /**
     * 单笔最大限额
     */
    private int singleMaxLimit;
}
