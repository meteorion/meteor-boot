package pers.meteor.system.mapper.user;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import pers.meteor.mybatis.core.query.BaseMapperX;
import pers.meteor.system.model.user.bo.UserBO;
import pers.meteor.system.model.user.dto.UserAuthInfo;
import pers.meteor.system.model.user.dto.UserExportDTO;
import pers.meteor.system.model.user.entity.User;
import pers.meteor.system.model.user.form.UserForm;
import pers.meteor.system.model.user.query.UserPageQuery;

import java.util.List;

/**
 * 用户持久层
 *
 * @author haoxr
 * @since 2022/1/14
 */
@Mapper
public interface UserMapper extends BaseMapperX<User> {

    default User selectByUsername(String username) {
        return selectOne(User::getUsername, username);
    }

    default User selectByMobile(String mobile) {
        return selectOne(User::getMobile, mobile);
    }

    default User selectByEmail(String email) {
        return selectOne(User::getEmail, email);
    }

    Page<UserBO> selectUserPage(Page<UserBO> page, UserPageQuery queryParams);

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
