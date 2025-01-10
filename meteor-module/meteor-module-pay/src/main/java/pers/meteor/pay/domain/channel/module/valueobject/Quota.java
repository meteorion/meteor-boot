package pers.meteor.pay.domain.channel.module.valueobject;

import lombok.Data;

/**
 * 通道额度
 *
 * @author meteor
 */
@Data
public class Quota {
    /**
     * 日订单限额
     */
    private int dailyOrderLimit;
    /**
     * 日限额
     */
    private int dailyLimit;
    /**
     * 月限额
     */
    private int monthLimit;
    /**
     * 单笔最低金额
     */
    private int singleMinLimit;
    /**
     * 单笔最大限额
     */
    private int singleMaxLimit;
}
