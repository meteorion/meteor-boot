package pers.meteor.shared.controller.admin.codegen.query;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import pers.meteor.common.pojo.command.PageQuery;

import java.util.List;

/**
 * 数据表分页查询对象
 *
 * @author Ray
 * @since 2.10.0
 */
@Schema(description = "数据表分页查询对象")
@Getter
@Setter
public class TablePageQuery extends PageQuery {

    @Schema(description="关键字(表名)")
    private String keywords;

    /**
     * 排除的表名
     */
    @JsonIgnore
    private List<String> excludeTables;
}
