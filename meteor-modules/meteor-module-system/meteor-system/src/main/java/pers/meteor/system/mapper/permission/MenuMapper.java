package pers.meteor.system.mapper.permission;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import pers.meteor.system.model.permission.entity.Menu;

import java.util.List;
import java.util.Set;

/**
 * 菜单访问层
 *
 * @author Ray
 * @since 2022/1/24
 */

@Mapper
public interface MenuMapper extends BaseMapper<Menu> {

    /**
     * 获取菜单路由列表
     *
     * @param roleCodes 角色编码集合
     */
    List<Menu> getMenusByRoleCodes(Set<String> roleCodes);

}
