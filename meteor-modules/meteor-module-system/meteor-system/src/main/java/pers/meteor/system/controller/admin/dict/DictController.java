package pers.meteor.system.controller.admin.dict;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pers.meteor.common.pojo.response.PageResponse;
import pers.meteor.common.pojo.response.SingleResponse;
import pers.meteor.system.mode.dict.form.DictForm;
import pers.meteor.system.mode.dict.query.DictPageQuery;
import pers.meteor.system.mode.dict.vo.DictPageVO;
import pers.meteor.system.mode.dict.vo.DictVO;
import pers.meteor.system.service.dict.DictService;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 字典控制层
 *
 * @author Ray
 * @since 2.9.0
 */
@Tag(name = "06.字典接口")
@RestController
@RequestMapping("/api/v1/dict")
@RequiredArgsConstructor
public class DictController {

    private final DictService dictService;

    @Operation(summary = "字典分页列表")
    @GetMapping("/page")
    public PageResponse<DictPageVO> getDictPage(
            DictPageQuery queryParams
    ) {
        Page<DictPageVO> result = dictService.getDictPage(queryParams);
        return PageResponse.success(result.getRecords(), result.getTotal(), result.getSize(), result.getCurrent());
    }

    @Operation(summary = "所有字典列表")
    @GetMapping("/list")
    public SingleResponse<List<DictVO>> getAllDictWithData() {
        List<DictVO> list = dictService.getAllDictWithData();
        return SingleResponse.success(list);
    }

    @Operation(summary = "字典表单")
    @GetMapping("/{id}/form")
    public SingleResponse<DictForm> getDictForm(
            @Parameter(description = "字典ID") @PathVariable Long id
    ) {
        DictForm formData = dictService.getDictForm(id);
        return SingleResponse.success(formData);
    }

    @Operation(summary = "新增字典")
    @PostMapping
    @PreAuthorize("@ss.hasPerm('sys:dict:add')")
    public SingleResponse<Boolean> saveDict(@Valid @RequestBody DictForm formData) {
        boolean result = dictService.saveDict(formData);
        return SingleResponse.success(result);
    }

    @Operation(summary = "修改字典")
    @PutMapping("/{id}")
    @PreAuthorize("@ss.hasPerm('sys:dict:edit')")
    public SingleResponse<Boolean> updateDict(
            @PathVariable Long id,
            @RequestBody DictForm DictForm
    ) {
        boolean status = dictService.updateDict(id, DictForm);
        return SingleResponse.success(status);
    }

    @Operation(summary = "删除字典")
    @DeleteMapping("/{ids}")
    @PreAuthorize("@ss.hasPerm('sys:dict:delete')")
    public SingleResponse<?> deleteDictionaries(
            @Parameter(description = "字典ID，多个以英文逗号(,)拼接") @PathVariable String ids
    ) {
        dictService.deleteDictByIds(Arrays.stream(ids.split(",")).collect(Collectors.toList()));
        return SingleResponse.success();
    }

}
