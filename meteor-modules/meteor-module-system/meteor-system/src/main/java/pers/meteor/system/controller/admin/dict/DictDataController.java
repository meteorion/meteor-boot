package pers.meteor.system.controller.admin.dict;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pers.meteor.common.pojo.Option;
import pers.meteor.common.pojo.response.PageResponse;
import pers.meteor.common.pojo.response.SingleResponse;
import pers.meteor.system.model.dict.form.DictDataForm;
import pers.meteor.system.model.dict.query.DictDataPageQuery;
import pers.meteor.system.model.dict.vo.DictDataPageVO;
import pers.meteor.system.service.dict.DictDataService;

import javax.validation.Valid;
import java.util.List;

/**
 * 字典数据控制层
 *
 * @author Ray
 * @since 2.9.0
 */
@Tag(name = "07.字典数据接口")
@RestController
@RequestMapping("/system/dict-data")
@RequiredArgsConstructor
public class DictDataController {

    private final DictDataService dictDataService;

    @Operation(summary = "字典数据分页列表")
    @GetMapping("/page")
    public PageResponse<DictDataPageVO> getDictDataPage(
            DictDataPageQuery queryParams
    ) {
        Page<DictDataPageVO> result = dictDataService.getDictDataPage(queryParams);
        return PageResponse.success(result.getRecords(), result.getTotal(), result.getSize(), result.getCurrent());
    }

    @Operation(summary = "获取字典数据表单")
    @GetMapping("/{id}/form")
    public SingleResponse<DictDataForm> getDictDataForm(
            @Parameter(description = "字典数据ID") @PathVariable Long id
    ) {
        DictDataForm formData = dictDataService.getDictDataForm(id);
        return SingleResponse.success(formData);
    }

    @Operation(summary = "新增字典数据")
    @PostMapping
    @PreAuthorize("@ss.hasPermission('sys:dict-data:add')")
    public SingleResponse<Boolean> saveDictData(@Valid @RequestBody DictDataForm formData) {
        boolean result = dictDataService.saveDictData(formData);
        return SingleResponse.success(result);
    }

    @Operation(summary = "修改字典数据")
    @PutMapping("/{id}")
    @PreAuthorize("@ss.hasPermission('sys:dict-data:edit')")
    public SingleResponse<Boolean> updateDictData(
            @PathVariable Long id,
            @RequestBody DictDataForm formData
    ) {
        boolean status = dictDataService.updateDictData(formData);
        return SingleResponse.success(status);
    }

    @Operation(summary = "删除字典数据")
    @DeleteMapping("/{ids}")
    @PreAuthorize("@ss.hasPermission('sys:dict-data:delete')")
    public SingleResponse<Void> deleteDictionaries(
            @Parameter(description = "字典ID，多个以英文逗号(,)拼接") @PathVariable String ids
    ) {
        dictDataService.deleteDictDataByIds(ids);
        return SingleResponse.success();
    }

    @Operation(summary = "字典数据列表")
    @GetMapping("/options/{dictCode}")
    public SingleResponse<List<Option<String>>> getDictDataList(
            @Parameter(description = "字典编码") @PathVariable String dictCode
    ) {
        List<Option<String>> options = dictDataService.getDictDataList(dictCode);
        return SingleResponse.success(options);
    }

}
