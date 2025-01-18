package pers.meteor.system.mode.dict.query;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import pers.meteor.common.pojo.command.PageQuery;

@Data
@EqualsAndHashCode(callSuper = false)
@Schema(description ="字典数据分页查询对象")
public class DictDataPageQuery extends PageQuery {

    @Schema(description="关键字(字典数据标签/值)")
    private String keywords;

    @Schema(description="字典编码")
    private String dictCode;

}
