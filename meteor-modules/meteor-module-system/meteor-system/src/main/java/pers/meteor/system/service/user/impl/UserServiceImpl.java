package pers.meteor.system.service.user.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pers.meteor.common.enums.UserStatusEnum;
import pers.meteor.common.pojo.Option;
import pers.meteor.common.utils.StringUtils;
import pers.meteor.security.core.utils.SecurityUtils;
import pers.meteor.system.convert.user.UserConverter;
import pers.meteor.system.enums.SystemConstants;
import pers.meteor.system.mapper.user.UserMapper;
import pers.meteor.system.model.user.bo.UserBO;
import pers.meteor.system.model.user.dto.UserExportDTO;
import pers.meteor.system.model.user.entity.User;
import pers.meteor.system.model.user.enums.ContactType;
import pers.meteor.system.model.user.form.*;
import pers.meteor.system.model.user.query.UserPageQuery;
import pers.meteor.system.model.user.vo.UserInfoVO;
import pers.meteor.system.model.user.vo.UserPageVO;
import pers.meteor.system.model.user.vo.UserProfileVO;
import pers.meteor.system.service.permission.PermissionService;
import pers.meteor.system.service.permission.UserRoleService;
import pers.meteor.system.service.user.UserService;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static pers.meteor.common.exception.util.ServiceExceptionUtil.exception;
import static pers.meteor.system.enums.SystemErrorConstants.*;

/**
 * 用户业务实现类
 *
 * @author haoxr
 * @since 2022/1/14
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    private final UserConverter userConverter = UserConverter.INSTANCE;

    private final PasswordEncoder passwordEncoder;
    private final UserRoleService userRoleService;
    private final PermissionService permissionService;
    private final StringRedisTemplate redisTemplate;
    private final UserMapper userMapper;

    @Override
    public Long saveUser(UserForm userForm) {
        // 校验数据
        validateUserCreate(userForm.getUsername(), userForm.getMobile());

        // 保存用户信息
        User user = userConverter.toEntity(userForm);
        user.setStatus(UserStatusEnum.ENABLE.getStatus());
        String password = StringUtils.isBlank(userForm.getPassword()) ? SystemConstants.DEFAULT_PASSWORD : userForm.getPassword();
        user.setPassword(encodePassword(password));
        userMapper.insert(user);

        // 保存用户角色
        if (CollectionUtil.isNotEmpty(userForm.getRoleIds())) {
            userRoleService.saveUserRoles(user.getId(), userForm.getRoleIds());
        }

        return user.getId();
    }

    @Override
    @Transactional
    public void updateUser(Long userId, UserForm userForm) {
        // 校验参数
        User user = validateUserUpdate(userId, userForm.getUsername(), userForm.getMobile());

        // 保存用户信息
        userMapper.updateById(userConverter.toEntity(userForm));

        // 保存用户角色
        if (CollectionUtil.isNotEmpty(userForm.getRoleIds())) {
            userRoleService.saveUserRoles(user.getId(), userForm.getRoleIds());
        }
    }

    @Override
    public void updateUserProfile(Long userId, UserProfileForm formData) {
        // 校验参数
        validateUserUpdate(userId, formData.getUsername(), formData.getMobile());

        // 保存用户信息
        User user = userConverter.toEntity(formData);
        user.setId(userId);
        userMapper.updateById(user);
    }

    @Override
    public void updateUserStatus(Long userId, Integer status) {
        User user = validateUserIdExist(userId);
        user.setStatus(status);
        userMapper.updateById(user);
    }

    @Override
    public void changePassword(Long userId, PasswordChangeForm form) {
        // 校验用户
        User user = validateUserIdExist(userId);

        // 校验原密码
        if (!isPasswordMatch(form.getOldPassword(), user.getPassword())) {
            throw exception(USER_PASSWORD_FAILED);
        }
        // 新旧密码不能相同
        if (isPasswordMatch(form.getNewPassword(), user.getPassword())) {
            throw exception(USER_PASSWORD_SAME);
        }

        User updateObj = new User();
        updateObj.setId(userId);
        updateObj.setPassword(encodePassword(form.getNewPassword()));
        userMapper.updateById(updateObj);
    }

    @Override
    public void resetPassword(Long userId, String password) {
        // 校验用户
        validateUserIdExist(userId);

        User updateObj = new User();
        updateObj.setId(userId);
        updateObj.setPassword(encodePassword(password));
        userMapper.updateById(updateObj);
    }

    @Override
    public void updateUserMobile(Long userId, String mobile) {
        // 校验用户
        validateUserIdExist(userId);

        User updateObj = new User();
        updateObj.setId(userId);
        updateObj.setMobile(mobile);
        userMapper.updateById(updateObj);
    }

    @Override
    public void updateUserEmail(Long userId, String email) {
        // 校验用户
        validateUserIdExist(userId);

        User updateObj = new User();
        updateObj.setId(userId);
        updateObj.setEmail(email);
        userMapper.updateById(updateObj);
    }

    @Override
    public void deleteUsers(String idsStr) {
        userMapper.deleteBatchIds(Arrays.asList(idsStr.split(",")));
    }

    @Override
    public IPage<UserPageVO> getUserPage(UserPageQuery queryParams) {

        // 参数构建
        int pageNum = queryParams.getPageIndex();
        int pageSize = queryParams.getPageSize();
        Page<UserBO> page = new Page<>(pageNum, pageSize);
        // 查询数据
        Page<UserBO> userPage = this.baseMapper.selectUserPage(page, queryParams);

        // 实体转换
        return userConverter.toPageVo(userPage);
    }

    @Override
    public UserForm getUserFormData(Long userId) {
        return this.baseMapper.selectUserFormData(userId);
    }

    @Override
    public List<UserExportDTO> listExportUsers(UserPageQuery queryParams) {
        return this.baseMapper.selectExportUsers(queryParams);
    }

    @Override
    public UserInfoVO getCurrentUserInfo() {

        String username = SecurityUtils.getLoginUserNickName();

        // 获取登录用户基础信息
        User user = this.getOne(new LambdaQueryWrapper<User>()
                .eq(User::getUsername, username)
                .select(
                        User::getId,
                        User::getUsername,
                        User::getNickname,
                        User::getAvatar
                )
        );
        // entity->VO
        UserInfoVO userInfoVO = userConverter.toUserInfoVo(user);

        // 用户角色集合
        Set<String> roles = SecurityUtils.getRoles();
        userInfoVO.setRoles(roles);

        // 用户权限集合
        if (CollectionUtil.isNotEmpty(roles)) {
            Set<String> perms = permissionService.getRolePermsFormCache(roles);
            userInfoVO.setPerms(perms);
        }
        return userInfoVO;
    }

    @Override
    public UserProfileVO getUserProfile(Long userId) {
        UserBO entity = this.baseMapper.selectUserProfile(userId);
        return userConverter.toProfileVO(entity);
    }

    @Override
    public boolean sendVerificationCode(String contact, ContactType type) {

        // 随机生成4位验证码
        String code = String.valueOf((int) ((Math.random() * 9 + 1) * 1000));
        // 发送验证码

        String verificationCodePrefix = null;
//        switch (type) {
//            case MOBILE:
//                // 获取修改密码的模板code
//                String changePasswordSmsTemplateCode = aliyunSmsProperties.getTemplateCodes().get("changePassword");
//                smsService.sendSms(contact, changePasswordSmsTemplateCode, "[{\"code\":\"" + code + "\"}]");
//                verificationCodePrefix = RedisConstants.MOBILE_VERIFICATION_CODE_PREFIX;
//                break;
//            case EMAIL:
//                mailService.sendMail(contact, "验证码", "您的验证码是：" + code);
//                verificationCodePrefix = RedisConstants.EMAIL_VERIFICATION_CODE_PREFIX;
//                break;
//            default:
//                throw new ServiceException("不支持的联系方式类型");
//        }
        // 存入 redis 用于校验, 5分钟有效
        redisTemplate.opsForValue().set(verificationCodePrefix + contact, code, 5, TimeUnit.MINUTES);
        return true;
    }

    @Override
    public List<Option<String>> listUserOptions() {
        List<User> list = this.list();
        if (CollectionUtil.isNotEmpty(list)) {
            return list.stream().map(user -> new Option<>(user.getId().toString(), user.getNickname())).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    private void validateUserCreate(String username, String mobile) {
        // 校验手机号
        validateMobileUnique(null, mobile);
        // 校验用户名
        validateUsernameUnique(null, username);
    }

    private User validateUserUpdate(Long userId, String username, String mobile) {
        User user = validateUserIdExist(userId);
        // 校验手机号
        validateMobileUnique(userId, mobile);
        // 校验用户名
        validateUsernameUnique(userId, username);

        return user;
    }

    private User validateUserIdExist(Long userId) {
        if (userId == null) {
            return null;
        }
        User user = this.getById(userId);
        if (user == null) {
            throw exception(USER_NOT_EXISTS);
        }
        return user;
    }

    private void validateUsernameUnique(Long userId, String username) {
        if (StringUtils.isBlank(username)) {
            return;
        }
        User user = userMapper.selectByUsername(username);
        if (user == null) {
            return;
        }
        if (userId == null || !userId.equals(user.getId())) {
            throw exception(USER_USERNAME_EXISTS);
        }
    }

    private void validateMobileUnique(Long userId, String mobile) {
        if (StringUtils.isBlank(mobile)) {
            return;
        }
        User user = userMapper.selectByMobile(mobile);
        if (user == null) {
            return;
        }
        if (userId == null || !userId.equals(user.getId())) {
            throw exception(USER_MOBILE_EXISTS);
        }
    }

    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

    private boolean isPasswordMatch(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
}
