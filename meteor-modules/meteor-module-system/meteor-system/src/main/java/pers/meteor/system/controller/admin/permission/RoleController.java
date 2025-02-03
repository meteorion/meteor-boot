package pers.meteor.system.controller.admin.permission;

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
import pers.meteor.system.model.permission.form.RoleForm;
import pers.meteor.system.model.permission.query.RolePageQuery;
import pers.meteor.system.model.permission.vo.RolePageVO;
import pers.meteor.system.service.permission.RoleService;

import javax.validation.Valid;
import java.util.List;


/**
 * 角色控制层
 *
 * @author Ray
 * @since 2022/10/16
 */
@Tag(name = "03.角色接口")
@RestController
@RequestMapping("/system/roles")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @Operation(summary = "角色分页列表")
    @GetMapping("/page")
    public PageResponse<RolePageVO> getRolePage(
             RolePageQuery queryParams
    ) {
        Page<RolePageVO> result = roleService.getRolePage(queryParams);
        return PageResponse.success(result.getRecords(), result.getTotal(), result.getSize(), result.getCurrent());
    }

    @Operation(summary = "角色下拉列表")
    @GetMapping("/options")
    public SingleResponse<List<Option<Long>>> listRoleOptions() {
        List<Option<Long>> list = roleService.listRoleOptions();
        return SingleResponse.success(list);
    }

    @Operation(summary = "新增角色")
    @PostMapping
    @PreAuthorize("@ss.hasPermission('sys:role:add')")
    public SingleResponse<?> addRole(@Valid @RequestBody RoleForm roleForm) {
        boolean result = roleService.saveRole(roleForm);
        return SingleResponse.success(result);
    }

    @Operation(summary = "角色表单数据")
    @GetMapping("/{roleId}/form")
    public SingleResponse<RoleForm> getRoleForm(
            @Parameter(description = "角色ID") @PathVariable Long roleId
    ) {
        RoleForm roleForm = roleService.getRoleForm(roleId);
        return SingleResponse.success(roleForm);
    }

    @Operation(summary = "修改角色")
    @PutMapping(value = "/{id}")
    @PreAuthorize("@ss.hasPermission('sys:role:edit')")
    public SingleResponse<?> updateRole(@Valid @RequestBody RoleForm roleForm) {
        boolean result = roleService.saveRole(roleForm);
        return SingleResponse.success(result);
    }

    @Operation(summary = "删除角色")
    @DeleteMapping("/{ids}")
    @PreAuthorize("@ss.hasPermission('sys:role:delete')")
    public SingleResponse<?> deleteRoles(
            @Parameter(description = "删除角色，多个以英文逗号(,)拼接") @PathVariable String ids
    ) {
        boolean result = roleService.deleteRoles(ids);
        return SingleResponse.success(result);
    }

    @Operation(summary = "修改角色状态")
    @PutMapping(value = "/{roleId}/status")
    public SingleResponse<?> updateRoleStatus(
            @Parameter(description = "角色ID") @PathVariable Long roleId,
            @Parameter(description = "状态(1:启用;0:禁用)") @RequestParam Integer status
    ) {
        boolean result = roleService.updateRoleStatus(roleId, status);
        return SingleResponse.success(result);
    }

    @Operation(summary = "获取角色的菜单ID集合")
    @GetMapping("/{roleId}/menuIds")
    public SingleResponse<List<Long>> getRoleMenuIds(
            @Parameter(description = "角色ID") @PathVariable Long roleId
    ) {
        List<Long> menuIds = roleService.getRoleMenuIds(roleId);
        return SingleResponse.success(menuIds);
    }

    @Operation(summary = "分配菜单(包括按钮权限)给角色")
    @PutMapping("/{roleId}/menus")
    public SingleResponse<?> assignMenusToRole(
            @PathVariable Long roleId,
            @RequestBody List<Long> menuIds
    ) {
        boolean result = roleService.assignMenusToRole(roleId, menuIds);
        return SingleResponse.success(result);
    }
}
