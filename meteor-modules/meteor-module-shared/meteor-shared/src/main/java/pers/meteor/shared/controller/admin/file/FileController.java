package pers.meteor.shared.controller.admin.file;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pers.meteor.common.pojo.response.SingleResponse;
import pers.meteor.shared.model.bo.file.FileInfo;
import pers.meteor.shared.service.file.FileService;

/**
 * 文件控制层
 *
 * @author Ray
 * @since 2022/10/16
 */
@Tag(name = "08.文件接口")
@RestController
@RequestMapping("/api/v1/files")
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;

    @PostMapping
    @Operation(summary = "文件上传")
    public SingleResponse<FileInfo> uploadFile(
            @Parameter(
                    name = "file",
                    description = "表单文件对象",
                    required = true,
                    in = ParameterIn.DEFAULT,
                    schema = @Schema(name = "file", format = "binary")
            )
            @RequestPart(value = "file") MultipartFile file
    ) {
        FileInfo fileInfo = fileService.uploadFile(file);
        return SingleResponse.success(fileInfo);
    }

    @DeleteMapping
    @Operation(summary = "文件删除")
    @SneakyThrows
    public SingleResponse<?> deleteFile(
            @Parameter(description = "文件路径") @RequestParam String filePath
    ) {
        boolean result = fileService.deleteFile(filePath);
        return SingleResponse.success(result);
    }
}
