package pers.meteor.system.api.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import pers.meteor.security.core.annotation.Inner;
import pers.meteor.system.api.user.vo.AdminUserVO;
import pers.meteor.common.constant.ServiceNameConstants;
import pers.meteor.common.entity.R;

/**
 * @author meteor
 */
@Inner
@RestController
@Slf4j
@RequiredArgsConstructor
public class RemoteUserServiceImpl implements RemoteUserService {


    @Inner
    @Override
    public R<AdminUserVO> getUserByUsername(String username) {
        AdminUserVO adminUser = new AdminUserVO();
        adminUser.setUserId(1L);
        adminUser.setUsername("admin");
        adminUser.setPassword("123456");
        return R.ok(adminUser);
    }
}
