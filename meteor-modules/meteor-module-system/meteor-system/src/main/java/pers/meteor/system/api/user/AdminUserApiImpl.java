package pers.meteor.system.api.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;
import pers.meteor.common.pojo.response.SingleResponse;
import pers.meteor.system.api.user.vo.AdminUserVO;
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
        return null;
    }

    @Override
    public SingleResponse<Boolean> isPasswordMatch(String mobile, String password) {
        return null;
    }
}
