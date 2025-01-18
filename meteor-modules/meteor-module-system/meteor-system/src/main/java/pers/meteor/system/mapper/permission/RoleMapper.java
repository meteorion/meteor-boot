package pers.meteor.system.mapper.permission;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import pers.meteor.system.model.permission.entity.Role;

import java.util.Set;

@Mapper
public interface RoleMapper extends BaseMapper<Role> {


    /**
     * 获取最大范围的数据权限
     *
     * @param roles
     * @return
     */
    Integer getMaximumDataScope(Set<String> roles);
}
