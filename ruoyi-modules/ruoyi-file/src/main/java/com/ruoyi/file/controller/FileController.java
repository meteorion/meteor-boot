package com.ruoyi.file.controller;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.text.CharSequenceUtil;
import com.meteor.common.cypto.core.annotation.Decrypt;
import com.ruoyi.common.core.domain.PageResult;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.utils.ServletUtils;
import com.ruoyi.file.controller.vo.file.FilePageReqVO;
import com.ruoyi.file.controller.vo.file.FileRespVO;
import com.ruoyi.file.controller.vo.file.FileUploadReqVO;
import com.ruoyi.file.convert.FileConvert;
import com.ruoyi.file.domain.File;
import com.ruoyi.file.service.FileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.annotation.security.PermitAll;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * 文件系统/文件管理
 *
 * @author metoer
 */
@Tag(name =  "管理后台 - 文件存储")
@RestController
@Validated
@Slf4j
public class FileController {

    @Resource
    private FileService fileService;

    @Decrypt
//    @PostMapping("/upload")
    @Operation(summary = "上传文件")
    public R<String> uploadFile(FileUploadReqVO uploadReqVO) throws Exception {
        MultipartFile file = uploadReqVO.getFile();
        String path = uploadReqVO.getPath();
        return R.ok(fileService.createFile(file.getOriginalFilename(), path, IoUtil.readBytes(file.getInputStream())));
    }

    /**
     * 上传文件
     *
     * @param parameters /
     * @return /
     */
    @Decrypt
    @PostMapping("/upload")
    public R<Object> test(String name) {
        return R.ok(name);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除文件")
    @Parameter(name = "id", description = "编号", required = true)
    public R<Boolean> deleteFile(@RequestParam("id") Long id) throws Exception {
        fileService.deleteFile(id);
        return R.ok(true);
    }

    @GetMapping("/{configId}/get/**")
    @PermitAll
    @Operation(summary = "下载文件")
    @Parameter(name = "configId", description = "配置编号",  required = true)
    public void getFileContent(HttpServletRequest request,
                               HttpServletResponse response,
                               @PathVariable("configId") Long configId) throws Exception {
        // 获取请求的路径
        String path = CharSequenceUtil.subAfter(request.getRequestURI(), "/get/", false);
        if (ObjectUtils.isEmpty(path)) {
            throw new IllegalArgumentException("结尾的 path 路径必须传递");
        }

        // 读取内容
        byte[] content = fileService.getFileContent(configId, path);
        if (content == null) {
            log.warn("[getFileContent][configId({}) path({}) 文件不存在]", configId, path);
            response.setStatus(HttpStatus.NOT_FOUND.value());
            return;
        }
        ServletUtils.writeAttachment(response, path, content);
    }

    @GetMapping("/page")
    @Operation(summary = "获得文件分页")
    public R<PageResult<FileRespVO>> getFilePage(@Valid FilePageReqVO pageVO) {
        PageResult<File> pageResult = fileService.getFilePage(pageVO);
        return R.ok(FileConvert.INSTANCE.convertPage(pageResult));
    }

}
