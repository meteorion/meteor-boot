package pers.meteor.system.mapper.user;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Mapper;
import pers.meteor.common.pojo.response.PageResponse;
import pers.meteor.mybatis.core.query.BaseMapperX;
import pers.meteor.mybatis.core.query.LambdaQueryWrapperX;
import pers.meteor.system.model.user.bo.UserBO;
import pers.meteor.system.model.user.dto.UserAuthInfo;
import pers.meteor.system.model.user.dto.UserExportDTO;
import pers.meteor.system.model.user.entity.AdminUser;
import pers.meteor.system.model.user.form.UserForm;
import pers.meteor.system.model.user.query.UserPageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 用户持久层
 *
 * @author haoxr
 * @since 2022/1/14
 */
@Mapper
public interface UserMapper extends BaseMapperX<AdminUser> {

    default AdminUser selectByUsername(String username) {
        return selectOne(AdminUser::getUsername, username);
    }

    default AdminUser selectByMobile(String mobile) {
        return selectOne(AdminUser::getMobile, mobile);
    }

    default AdminUser selectByEmail(String email) {
        return selectOne(AdminUser::getEmail, email);
    }

    default List<AdminUser> selectListByDeptIds(Collection<Long> deptIds) {
        return selectList(AdminUser::getDeptId, deptIds);
    }

    default PageResponse<AdminUser> selectUserPage(UserPageQuery queryParams, Collection<Long> deptIds, Collection<Long> userIds) {
        return selectPage(queryParams, new LambdaQueryWrapperX<AdminUser>()
                .eqIfPresent(AdminUser::getStatus, queryParams.getStatus())
                .betweenIfPresent(AdminUser::getCreateTime, queryParams.getCreateTime())
                .inIfPresent(AdminUser::getDeptId, deptIds)
                .inIfPresent(AdminUser::getId, userIds)
                .and(StringUtils.isNotBlank(queryParams.getKeywords()),c -> c
                        .like(AdminUser::getUsername, queryParams.getKeywords())
                        .or()
                        .like(AdminUser::getMobile, queryParams.getKeywords()))
                .orderByDesc(AdminUser::getId));
    }

//    Page<UserBO> selectUserPage(Page<UserBO> page, UserPageQuery queryParams);

    /**
     * 获取用户表单详情
     *
     * @param userId 用户ID
     * @return
     */
    UserForm selectUserFormData(Long userId);

    /**
     * 根据用户名获取认证信息
     *
     * @param username
     * @return
     */
    UserAuthInfo selectUserAuthInfo(String username);

    /**
     * 根据微信openid获取用户认证信息
     *
     * @param openid 微信openid
     * @return
     */
    UserAuthInfo selectUserAuthInfoByOpenId(String openid);

    /**
     * 获取导出用户列表
     *
     * @param queryParams
     * @return
     */
    List<UserExportDTO> selectExportUsers(UserPageQuery queryParams);

    /**
     * 获取用户个人中心信息
     *
     * @param userId 用户ID
     * @return
     */
    UserBO selectUserProfile(Long userId);
}
