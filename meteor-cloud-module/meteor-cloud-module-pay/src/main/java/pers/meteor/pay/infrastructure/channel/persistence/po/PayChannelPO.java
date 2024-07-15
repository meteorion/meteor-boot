package pers.meteor.pay.infrastructure.channel.persistence.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import pers.meteor.mybatis.core.BasePO;

/**
 * @author meteor
 */
@TableName("pay_channel")
@Data
@EqualsAndHashCode(callSuper = true)
public class PayChannelPO extends BasePO {
    /**
     * 支付通道id
     */
    @TableId(type = IdType.AUTO)
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
    /**
     * 日限额
     */
    private int dailyLimit;
    /**
     * 月限额
     */
    private int monthLimit;
}
