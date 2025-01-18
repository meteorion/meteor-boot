package pers.meteor.system.convert.permission;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pers.meteor.system.mode.permission.entity.Menu;
import pers.meteor.system.mode.permission.form.MenuForm;
import pers.meteor.system.mode.permission.vo.MenuVO;

/**
 * 菜单对象转换器
 *
 * @author Ray Hao
 * @since 2024/5/26
 */
@Mapper(componentModel = "spring")
public interface MenuConverter {

    MenuVO toVo(Menu entity);

    @Mapping(target = "params", ignore = true)
    MenuForm toForm(Menu entity);

    @Mapping(target = "params", ignore = true)
    Menu toEntity(MenuForm menuForm);

}