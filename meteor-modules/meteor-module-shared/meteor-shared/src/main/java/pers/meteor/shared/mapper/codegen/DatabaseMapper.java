package pers.meteor.shared.mapper.codegen;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import pers.meteor.mybatis.core.BaseEntity;
import pers.meteor.shared.model.codegen.query.TablePageQuery;
import pers.meteor.shared.model.codegen.vo.TablePageVO;
import pers.meteor.shared.model.codegen.bo.ColumnMetaData;
import pers.meteor.shared.model.codegen.bo.TableMetaData;

import java.util.List;


/**
 * 数据库映射层
 *
 * @author Ray
 * @since 2.9.0
 */
@Mapper
public interface DatabaseMapper extends BaseMapper<BaseEntity> {

    /**
     * 获取表分页列表
     *
     * @param page
     * @param queryParams
     * @return
     */
    Page<TablePageVO> getTablePage(Page<TablePageVO> page, TablePageQuery queryParams);

    /**
     * 获取表字段列表
     *
     * @param tableName
     * @return
     */
    List<ColumnMetaData> getTableColumns(String tableName);

    /**
     * 获取表元数据
     *
     * @param tableName
     * @return
     */
    TableMetaData getTableMetadata(String tableName);
}
