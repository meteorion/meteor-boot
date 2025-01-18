package pers.meteor.system.service.permission;

import com.baomidou.mybatisplus.extension.service.IService;
import pers.meteor.common.pojo.Option;
import pers.meteor.system.mode.permission.entity.Menu;
import pers.meteor.system.mode.permission.form.MenuForm;
import pers.meteor.system.mode.permission.query.MenuQuery;
import pers.meteor.system.mode.permission.vo.MenuVO;
import pers.meteor.system.mode.permission.vo.RouteVO;

import java.util.List;

/**
 * 菜单业务接口
 * 
 * @author haoxr
 * @since 2020/11/06
 */
public interface MenuService extends IService<Menu> {

    /**
     * 获取菜单表格列表
     */
    List<MenuVO> listMenus(MenuQuery queryParams);

    /**
     * 获取菜单下拉列表
     *
     * @param onlyParent 是否只查询父级菜单
     */
    List<Option<Long>> listMenuOptions(boolean onlyParent);

    /**
     * 新增菜单
     *
     * @param menuForm  菜单表单对象
     */
    boolean saveMenu(MenuForm menuForm);

    /**
     * 获取路由列表
     */
    List<RouteVO> getCurrentUserRoutes();

    /**
     * 修改菜单显示状态
     * 
     * @param menuId 菜单ID
     * @param visible 是否显示(1-显示 0-隐藏)
     */
    boolean updateMenuVisible(Long menuId, Integer visible);

    /**
     * 获取菜单表单数据
     *
     * @param id 菜单ID
     */
    MenuForm getMenuForm(Long id);

    /**
     * 删除菜单
     *
     * @param id 菜单ID
     */
    boolean deleteMenu(Long id);

//    /**
//     * 代码生成时添加菜单
//     *
//     * @param parentMenuId 父菜单ID
//     * @param genConfig   实体名
//     */
//    void addMenuForCodegen(Long parentMenuId, GenConfig genConfig);
}
