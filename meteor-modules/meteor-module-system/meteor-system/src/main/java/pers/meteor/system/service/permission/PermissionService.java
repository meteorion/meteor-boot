package pers.meteor.system.service.permission;

import java.util.Collection;
import java.util.Set;

/**
 * @author 钟宗兵
 * @since 1.0.0
 */
public interface PermissionService {
    /**
     * 判断当前登录用户是否拥有操作权限
     *
     * @param requiredPerm 所需权限
     * @return 是否有权限
     */
    boolean hasPerm(String requiredPerm);

    /**
     * 从缓存中获取角色权限列表
     *
     * @param roleCodes 角色编码集合
     * @return 角色权限列表
     */
    Set<String> getRolePermsFormCache(Set<String> roleCodes);

    /**
     * 从数据库中获取角色权限列表
     *
     * @param roleIds 角色编码集合
     * @return 角色权限列表
     */
    Set<Long> getUserIdByRoleId(Collection<Long> roleIds);
}
