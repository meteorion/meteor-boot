package com.ruoyi.file.controller;

import com.ruoyi.common.core.domain.PageResult;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.file.controller.vo.config.FileConfigCreateReqVO;
import com.ruoyi.file.controller.vo.config.FileConfigPageReqVO;
import com.ruoyi.file.controller.vo.config.FileConfigRespVO;
import com.ruoyi.file.controller.vo.config.FileConfigUpdateReqVO;
import com.ruoyi.file.convert.FileConfigConvert;
import com.ruoyi.file.domain.FileStrogeClient;
import com.ruoyi.file.service.FileStrogeClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

@Tag(name =  "管理后台 - 文件配置")
@RestController
@RequestMapping("/config")
@Validated
public class FileConfigController {

    @Resource
    private FileStrogeClientService fileStrogeClientService;

    @PostMapping("/create")
    @Operation(summary = "创建文件配置")
    public R<Long> createFileConfig(@Valid @RequestBody FileConfigCreateReqVO createReqVO) {
        return R.ok(fileStrogeClientService.createFileConfig(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新文件配置")
    public R<Boolean> updateFileConfig(@Valid @RequestBody FileConfigUpdateReqVO updateReqVO) {
        fileStrogeClientService.updateFileConfig(updateReqVO);
        return R.ok(true);
    }

    @PutMapping("/update-master")
    @Operation(summary = "更新文件配置为 Master")
    public R<Boolean> updateFileConfigMaster(@RequestParam("id") Long id) {
        fileStrogeClientService.updateFileConfigMaster(id);
        return R.ok(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除文件配置")
    @Parameter(name = "id", description = "编号", required = true)
    public R<Boolean> deleteFileConfig(@RequestParam("id") Long id) {
        fileStrogeClientService.deleteFileConfig(id);
        return R.ok(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得文件配置")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    public R<FileConfigRespVO> getFileConfig(@RequestParam("id") Long id) {
        FileStrogeClient fileConfig = fileStrogeClientService.getFileConfig(id);
        return R.ok(FileConfigConvert.INSTANCE.convert(fileConfig));
    }

    @GetMapping("/page")
    @Operation(summary = "获得文件配置分页")
    public R<PageResult<FileConfigRespVO>> getFileConfigPage(@Valid FileConfigPageReqVO pageVO) {
        PageResult<FileStrogeClient> pageResult = fileStrogeClientService.getFileConfigPage(pageVO);
        return R.ok(FileConfigConvert.INSTANCE.convertPage(pageResult));
    }

    @GetMapping("/test")
    @Operation(summary = "测试文件配置是否正确")
    public R<String> testFileConfig(@RequestParam("id") Long id) throws Exception {
        String url = fileStrogeClientService.testFileConfig(id);
        return R.ok(url);
    }
}
