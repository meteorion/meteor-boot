package pers.meteor.shared.service.codegen;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import pers.meteor.shared.model.codegen.query.TablePageQuery;
import pers.meteor.shared.model.codegen.vo.CodegenPreviewVO;
import pers.meteor.shared.model.codegen.vo.TablePageVO;

import java.util.List;

/**
 * 代码生成配置接口
 *
 * @author Ray
 * @since 2.10.0
 */
public interface CodegenService {

    /**
     * 获取数据表分页列表
     *
     * @param queryParams 查询参数
     * @return
     */
    Page<TablePageVO> getTablePage(TablePageQuery queryParams);

    /**
     * 获取预览生成代码
     *
     * @param tableName 表名
     * @return
     */
    List<CodegenPreviewVO> getCodegenPreviewData(String tableName);

    /**
     * 下载代码
     * @param tableNames 表名
     * @return
     */
    byte[] downloadCode(String[] tableNames);
}
