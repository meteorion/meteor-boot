package pers.meteor.shared.controller.admin.config;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pers.meteor.common.pojo.response.PageResponse;
import pers.meteor.common.pojo.response.SingleResponse;
import pers.meteor.shared.model.config.form.ConfigForm;
import pers.meteor.shared.model.config.query.ConfigPageQuery;
import pers.meteor.shared.model.config.vo.ConfigVO;
import pers.meteor.shared.service.config.ConfigService;

import javax.validation.Valid;

/**
 * 系统配置前端控制层
 *
 * @author Theo
 * @since 2024-07-30 11:25
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "10.系统配置")
@RequestMapping("/config")
public class ConfigController {

    private final ConfigService configService;

    @Operation(summary = "系统配置分页列表")
    @GetMapping("/page")
    @PreAuthorize("@ss.hasPermission('sys:config:query')")
    public PageResponse<ConfigVO> page(@ParameterObject ConfigPageQuery configPageQuery) {
        IPage<ConfigVO> result = configService.page(configPageQuery);
        return PageResponse.success(result.getRecords(), result.getTotal(), result.getSize(), result.getCurrent());
    }

    @Operation(summary = "新增系统配置")
    @PostMapping
    @PreAuthorize("@ss.hasPermission('sys:config:add')")
    public SingleResponse<?> save(@RequestBody @Valid ConfigForm configForm) {
        return SingleResponse.success(configService.save(configForm));
    }

    @Operation(summary = "获取系统配置表单数据")
    @GetMapping("/{id}/form")
    public SingleResponse<ConfigForm> getConfigForm(
            @Parameter(description = "系统配置ID") @PathVariable Long id
    ) {
        ConfigForm formData = configService.getConfigFormData(id);
        return SingleResponse.success(formData);
    }

    @Operation(summary = "刷新系统配置缓存")
    @PutMapping("/refresh")
    @PreAuthorize("@ss.hasPermission('sys:config:refresh')")
    public SingleResponse<Boolean> refreshCache() {
        return SingleResponse.success(configService.refreshCache());
    }

    @Operation(summary = "修改系统配置")
    @PutMapping(value = "/{id}")
    @PreAuthorize("@ss.hasPermission('sys:config:update')")
    public SingleResponse<?> update(@Valid @PathVariable Long id, @RequestBody ConfigForm configForm) {
        return SingleResponse.success(configService.edit(id, configForm));
    }

    @Operation(summary = "删除系统配置")
    @DeleteMapping("/{id}")
    @PreAuthorize("@ss.hasPermission('sys:config:delete')")
    public SingleResponse<?> delete(@PathVariable Long id) {
        return SingleResponse.success(configService.delete(id));
    }

}
