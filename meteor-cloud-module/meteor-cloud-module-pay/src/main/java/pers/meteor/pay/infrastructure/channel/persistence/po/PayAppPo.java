package pers.meteor.pay.infrastructure.channel.persistence.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import pers.meteor.mybatis.core.BasePo;

/**
 * @author meteor
 */
@TableName("pay_app")
@Data
@EqualsAndHashCode(callSuper = true)
public class PayAppPo extends BasePo {
    /**
     * 支付通道id
     */
    @TableId(type = IdType.AUTO)
    private Long appId;
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
     * 订单有效时间
     */
    private Long expiry;
    /**
     * 日限额
     */
    private int dailyLimit;
    /**
     * 月限额
     */
    private int monthLimit;
}
