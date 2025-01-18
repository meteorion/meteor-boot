package pers.meteor.system.mapper.user;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import pers.meteor.system.mode.user.bo.UserBO;
import pers.meteor.system.mode.user.dto.UserAuthInfo;
import pers.meteor.system.mode.user.dto.UserExportDTO;
import pers.meteor.system.mode.user.entity.User;
import pers.meteor.system.mode.user.form.UserForm;
import pers.meteor.system.mode.user.query.UserPageQuery;

import java.util.List;

/**
 * 用户持久层
 *
 * @author haoxr
 * @since 2022/1/14
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

    /**
     * 获取用户分页列表
     *
     * @param page
     * @param queryParams 查询参数
     * @return
     */
    Page<UserBO> getUserPage(Page<UserBO> page, UserPageQuery queryParams);

    /**
     * 获取用户表单详情
     *
     * @param userId 用户ID
     * @return
     */
    UserForm getUserFormData(Long userId);

    /**
     * 根据用户名获取认证信息
     *
     * @param username
     * @return
     */
    UserAuthInfo getUserAuthInfo(String username);

    /**
     * 根据微信openid获取用户认证信息
     *
     * @param openid 微信openid
     * @return
     */
    UserAuthInfo getUserAuthInfoByOpenId(String openid);

    /**
     * 获取导出用户列表
     *
     * @param queryParams
     * @return
     */
    List<UserExportDTO> listExportUsers(UserPageQuery queryParams);

    /**
     * 获取用户个人中心信息
     *
     * @param userId 用户ID
     * @return
     */
    UserBO getUserProfile(Long userId);


}
