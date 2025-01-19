package pers.meteor.system.api.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;
import pers.meteor.common.pojo.response.SingleResponse;
import pers.meteor.common.utils.bean.BeanUtils;
import pers.meteor.system.api.user.vo.AdminUserVO;
import pers.meteor.system.model.user.entity.AdminUser;
import pers.meteor.system.service.user.UserService;

/**
 * @author meteor
 */
@RestController
@Slf4j
@RequiredArgsConstructor
public class AdminUserApiImpl implements AdminUserApi {

    private final UserService userService;

    @Override
    public SingleResponse<AdminUserVO> getUserByMobile(String mobile) {
        AdminUser user = userService.getUserByMobile(mobile);
        return SingleResponse.success(BeanUtils.toBean(user, AdminUserVO.class));
    }

    @Override
    public SingleResponse<AdminUserVO> getUserByUsername(String username) {
        AdminUser user = userService.getUserByUsername(username);
        return SingleResponse.success(BeanUtils.toBean(user, AdminUserVO.class));
    }

    @Override
    public SingleResponse<Boolean> isPasswordMatch(Long userId, String password) {
        return SingleResponse.success(userService.validatePassword(userId, password));
    }
}
