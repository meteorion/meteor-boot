package pers.meteor.shared.model.config.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import pers.meteor.mybatis.core.BaseEntity;

/**
 * 系统配置 实体
 *
 * @author Theo
 * @since 2024-07-29 11:17:26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Schema(description = "系统配置")
@TableName("sys_config")
public class Config extends BaseEntity {
    /**
     * 参数主键
     */
    @TableId
    private Long id;
    /**
     * 配置名称
     */
    private String configName;

    /**
     * 配置键
     */
    private String configKey;

    /**
     * 配置值
     */
    private String configValue;

    /**
     * 描述、备注
     */
    private String remark;

    /**
     * 逻辑删除标识(0-未删除 1-已删除)
     */
    private Integer isDeleted;

}
