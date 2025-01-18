package pers.meteor.system.service.permission;


import com.baomidou.mybatisplus.extension.service.IService;
import pers.meteor.system.model.permission.entity.UserRole;

import java.util.List;

public interface UserRoleService extends IService<UserRole> {

    /**
     * 保存用户角色
     *
     * @param userId
     * @param roleIds
     * @return
     */
    boolean saveUserRoles(Long userId, List<Long> roleIds);

    /**
     * 判断角色是否存在绑定的用户
     *
     * @param roleId 角色ID
     * @return true：已分配 false：未分配
     */
    boolean hasAssignedUsers(Long roleId);
}
