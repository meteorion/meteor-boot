package pers.meteor.shared.model.config.form;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 系统配置 表单实体
 *
 * @author Theo
 * @since 2024-07-29 11:17:26
 */
@Data
@Schema(description = "系统配置Form实体")
public class ConfigForm implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "主键")
    private Long id;

    @NotBlank(message = "配置名称不能为空")
    @Schema(description = "配置名称")
    private String configName;

    @NotBlank(message = "配置键不能为空")
    @Schema(description = "配置键")
    private String configKey;

    @NotBlank(message = "配置值不能为空")
    @Schema(description = "配置值")
    private String configValue;

    @Schema(description = "描述、备注")
    private String remark;
}
