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
@TableName("cif_risk_control_config")
public class RiskControlPo {
    @TableId(type = IdType.AUTO)
    private Integer id;
    /**
     * 通道id
     */
    private String operationType;
    /**
     * 服务id：beanName
     */
    private String riskControlType;
    /**
     * 启用状态
     */
    @TableField("is_open")
    private Boolean open;
    /**
     * 参数组id
     */
    private String paramGroupIds;
}

