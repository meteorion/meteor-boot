package pers.meteor.shared.controller.admin.codegen;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import pers.meteor.common.pojo.response.PageResponse;
import pers.meteor.common.pojo.response.SingleResponse;
import pers.meteor.shared.controller.admin.codegen.form.GenConfigForm;
import pers.meteor.shared.controller.admin.codegen.query.TablePageQuery;
import pers.meteor.shared.controller.admin.codegen.vo.CodegenPreviewVO;
import pers.meteor.shared.controller.admin.codegen.vo.TablePageVO;
import pers.meteor.shared.framework.codegen.config.CodegenProperties;
import pers.meteor.shared.service.codegen.CodegenService;
import pers.meteor.shared.service.codegen.GenConfigService;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @author meteor
 */
@Tag(name = "11.代码生成")
@RestController
@RequestMapping("/api/v1/codegen")
@RequiredArgsConstructor
@Slf4j
public class CodegenController {

    private final CodegenService codegenService;
    private final GenConfigService genConfigService;
    private final CodegenProperties codegenProperties;

    @Operation(summary = "获取数据表分页列表")
    @GetMapping("/table/page")
    public PageResponse<TablePageVO> getTablePage(TablePageQuery queryParams) {
        Page<TablePageVO> result = codegenService.getTablePage(queryParams);
        return PageResponse.success(result.getRecords(), result.getTotal(), result.getSize(), result.getCurrent());
    }

    @Operation(summary = "获取代码生成配置")
    @GetMapping("/{tableName}/config")
    public SingleResponse<GenConfigForm> getGenConfigFormData(@Parameter(description = "表名", example = "sys_user") @PathVariable String tableName) {
        GenConfigForm formData = genConfigService.getGenConfigFormData(tableName);
        return SingleResponse.success(formData);
    }

    @Operation(summary = "保存代码生成配置")
    @PostMapping("/{tableName}/config")
    public SingleResponse<?> saveGenConfig(@RequestBody GenConfigForm formData) {
        genConfigService.saveGenConfig(formData);
        return SingleResponse.success();
    }

    @Operation(summary = "删除代码生成配置")
    @DeleteMapping("/{tableName}/config")
    public SingleResponse<?> deleteGenConfig(
            @Parameter(description = "表名", example = "sys_user") @PathVariable String tableName
    ) {
        genConfigService.deleteGenConfig(tableName);
        return SingleResponse.success();
    }

    @Operation(summary = "获取预览生成代码")
    @GetMapping("/{tableName}/preview")
    public SingleResponse<List<CodegenPreviewVO>> getTablePreviewData(@PathVariable String tableName) {
        List<CodegenPreviewVO> list = codegenService.getCodegenPreviewData(tableName);
        return SingleResponse.success(list);
    }

    @Operation(summary = "下载代码")
    @GetMapping("/{tableName}/download")
    public void downloadZip(HttpServletResponse response, @PathVariable String tableName) throws UnsupportedEncodingException {
        String[] tableNames = tableName.split(",");
        byte[] data = codegenService.downloadCode(tableNames);

        response.reset();
        response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(codegenProperties.getDownloadFileName(), StandardCharsets.UTF_8.name()));
        response.setContentType("application/octet-stream; charset=UTF-8");

        try (ServletOutputStream outputStream = response.getOutputStream()) {
            outputStream.write(data);
            outputStream.flush();
        } catch (IOException e) {
            log.error("Error while writing the zip file to response", e);
            throw new RuntimeException("Failed to write the zip file to response", e);
        }
    }
}
