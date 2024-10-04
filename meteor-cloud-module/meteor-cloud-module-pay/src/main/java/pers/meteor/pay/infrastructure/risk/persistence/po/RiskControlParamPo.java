package pers.meteor.pay.infrastructure.risk.persistence.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author meteor
 */
@Data
@TableName("cif_risk_control_params_config")
public class RiskControlParamPo {
    @TableId(type = IdType.AUTO)
    private Integer id;
    /**
     * 参数组id
     */
    private String groupId;
    /**
     * 风控规则
     */
    private String ruleType;
    /**
     * 参数值
     */
    private String value;
    /**
     * 排序
     */
    @TableField("`order`")
    private Integer order;
    /**
     * 状态
     */
    @TableField("is_enabled")
    private boolean enabled;
}
