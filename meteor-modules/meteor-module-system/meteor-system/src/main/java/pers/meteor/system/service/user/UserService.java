package pers.meteor.system.service.user;

import com.baomidou.mybatisplus.core.metadata.IPage;
import pers.meteor.common.pojo.Option;
import pers.meteor.common.pojo.response.PageResponse;
import pers.meteor.system.model.user.dto.UserExportDTO;
import pers.meteor.system.model.user.entity.AdminUser;
import pers.meteor.system.model.user.enums.ContactType;
import pers.meteor.system.model.user.form.*;
import pers.meteor.system.model.user.query.UserPageQuery;
import pers.meteor.system.model.user.vo.UserInfoVO;
import pers.meteor.system.model.user.vo.UserProfileVO;

import java.util.List;

/**
 * 用户业务接口
 *
 * @author haoxr
 * @since 2022/1/14
 */
public interface UserService {

    /**
     * 新增用户
     *
     * @param userForm 用户表单对象
     * @return /
     */
    Long saveUser(UserForm userForm);

    /**
     * 修改用户
     *
     * @param userId   用户ID
     * @param userForm 用户表单对象
     */
    void updateUser(Long userId, UserForm userForm);

    /**
     * 修改个人中心用户信息
     *
     * @param userId 用户ID
     * @param formData 表单数据
     */
    void updateUserProfile(Long userId, UserProfileForm formData);

    /**
     * 修改用户状态
     *
     * @param userId 用户ID
     * @param status 用户状态(1:启用;0:禁用)
     */
    void updateUserStatus(Long userId, Integer status);

    /**
     * 修改用户密码
     *
     * @param userId 用户ID
     * @param data   修改密码表单数据
     */
    void changePassword(Long userId, PasswordChangeForm data);

    /**
     * 重置用户密码
     *
     * @param userId   用户ID
     * @param password 重置后的密码
     */
    void resetPassword(Long userId, String password);

    /**
     * 修改当前用户手机号
     *
     * @param userId 用户ID
     * @param mobile 手机号
     */
    void updateUserMobile(Long userId, String mobile);

    /**
     * 修改当前用户邮箱
     *
     * @param userId 用户ID
     * @param email  邮箱
     */
    void updateUserEmail(Long userId, String email);

    /**
     * 删除用户
     *
     * @param idsStr 用户ID，多个以英文逗号(,)分割
     */
    void deleteUsers(String idsStr);

    /**
     * 根据用户ID获取用户信息
     *
     * @param userId 用户ID
     * @return 用户信息
     */
    AdminUser getUser(Long userId);

    /**
     * 根据用户名获取用户信息
     *
     * @param username 用户名
     * @return 用户信息
     */
    AdminUser getUserByUsername(String username);

    /**
     * 根据手机号获取用户信息
     *
     * @param mobile 手机号
     * @return 用户信息
     */
    AdminUser getUserByMobile(String mobile);

    /**
     * 用户分页列表 /
     *
     * @return /
     */
    PageResponse<AdminUser> getUserPage(UserPageQuery queryParams);

    /**
     * 获取部门下所有用户
     *
     * @param deptIds 部门ID集合
     * @return 用户列表
     */
    List<AdminUser> getUserByDeptIds(List<Long> deptIds);

    /**
     * 根据用户ID集合获取用户列表
     *
     * @param userIds 用户ID集合
     * @return 用户列表
     */
    List<AdminUser> getUsers(List<Long> userIds);

    /**
     * 获取用户表单数据
     *
     * @param userId /
     * @return /
     */
    UserForm getUserFormData(Long userId);

    /**
     * 获取导出用户列表
     *
     * @param queryParams 查询参数
     * @return /
     */
    List<UserExportDTO> listExportUsers(UserPageQuery queryParams);

    /**
     * 获取登录用户信息
     *
     * @return /
     */
    UserInfoVO getCurrentUserInfo();

    /**
     * 获取个人中心用户信息
     *
     * @return /
     */
    UserProfileVO getUserProfile(Long userId);

    /**
     * 发送验证码
     *
     * @param contact 联系方式
     * @param type    联系方式类型
     * @return /
     */
    boolean sendVerificationCode(String contact, ContactType type);

    /**
     * 获取用户选项列表
     *
     * @return 用户选项列表
     */
    List<Option<String>> listUserOptions();

    /**
     * 校验密码是否匹配
     *
     * @param userId       用户id
     * @param rawPassword   未加密的密码
     * @return 是否匹配
     */
    boolean validatePassword(Long userId, String rawPassword);
}
