package pers.meteor.system.controller.admin.permission;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pers.meteor.common.pojo.Option;
import pers.meteor.common.pojo.response.SingleResponse;
import pers.meteor.system.model.permission.form.MenuForm;
import pers.meteor.system.model.permission.query.MenuQuery;
import pers.meteor.system.model.permission.vo.MenuVO;
import pers.meteor.system.model.permission.vo.RouteVO;
import pers.meteor.system.service.permission.MenuService;

import java.util.List;

/**
 * 菜单控制层
 *
 * @author Ray
 * @since 2020/11/06
 */
@Tag(name = "04.菜单接口")
@RestController
@RequestMapping("/menus")
@RequiredArgsConstructor
@Slf4j
public class MenuController {

    private final MenuService menuService;

    @Operation(summary = "菜单列表")
    @GetMapping
    public SingleResponse<List<MenuVO>> listMenus(MenuQuery queryParams) {
        List<MenuVO> menuList = menuService.listMenus(queryParams);
        return SingleResponse.success(menuList);
    }

    @Operation(summary = "菜单下拉列表")
    @GetMapping("/options")
    public SingleResponse<List<Option<Long>>> listMenuOptions(
          @Parameter(description = "是否只查询父级菜单")
          @RequestParam(required = false, defaultValue = "false") boolean onlyParent
    ) {
        List<Option<Long>> menus = menuService.listMenuOptions(onlyParent);
        return SingleResponse.success(menus);
    }

    @Operation(summary = "菜单路由列表")
    @GetMapping("/routes")
    public SingleResponse<List<RouteVO>> getCurrentUserRoutes() {
        List<RouteVO> routeList = menuService.getCurrentUserRoutes();
        return SingleResponse.success(routeList);
    }

    @Operation(summary = "菜单表单数据")
    @GetMapping("/{id}/form")
    public SingleResponse<MenuForm> getMenuForm(
            @Parameter(description = "菜单ID") @PathVariable Long id
    ) {
        MenuForm menu = menuService.getMenuForm(id);
        return SingleResponse.success(menu);
    }

    @Operation(summary = "新增菜单")
    @PostMapping
    @PreAuthorize("@ss.hasPermission('sys:menu:add')")
    public SingleResponse<?> addMenu(@RequestBody MenuForm menuForm) {
        boolean result = menuService.saveMenu(menuForm);
        return SingleResponse.success(result);
    }

    @Operation(summary = "修改菜单")
    @PutMapping(value = "/{id}")
    @PreAuthorize("@ss.hasPermission('sys:menu:edit')")
    public SingleResponse<?> updateMenu(
            @RequestBody MenuForm menuForm
    ) {
        boolean result = menuService.saveMenu(menuForm);
        return SingleResponse.success(result);
    }

    @Operation(summary = "删除菜单")
    @DeleteMapping("/{id}")
    @PreAuthorize("@ss.hasPermission('sys:menu:delete')")
    public SingleResponse<?> deleteMenu(
            @Parameter(description = "菜单ID，多个以英文(,)分割") @PathVariable("id") Long id
    ) {
        boolean result = menuService.deleteMenu(id);
        return SingleResponse.success(result);
    }

    @Operation(summary = "修改菜单显示状态")
    @PatchMapping("/{menuId}")
    public SingleResponse<?> updateMenuVisible(
            @Parameter(description = "菜单ID") @PathVariable Long menuId,
            @Parameter(description = "显示状态(1:显示;0:隐藏)") Integer visible

    ) {
        boolean result = menuService.updateMenuVisible(menuId, visible);
        return SingleResponse.success(result);
    }

}

