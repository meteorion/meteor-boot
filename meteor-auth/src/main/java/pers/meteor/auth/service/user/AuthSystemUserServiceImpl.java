package pers.meteor.auth.service.user;

import org.springframework.stereotype.Service;
import pers.meteor.auth.api.dto.AuthUserDetail;
import pers.meteor.auth.convert.AuthConvert;
import pers.meteor.common.enums.UserTypeEnum;
import pers.meteor.system.api.user.AdminUserApi;
import pers.meteor.system.api.user.vo.AdminUserVO;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * @author meteor
 */
@Service
public class AuthSystemUserServiceImpl implements AuthUserService {

    @Resource
    private AdminUserApi adminUserApi;
    @Resource
    private AuthUserServiceFactory authUserServiceFactory;

    @PostConstruct
    public void registerService() {
        authUserServiceFactory.registerService(UserTypeEnum.ADMIN.getValue(), this);
    }

    @Override
    public AuthUserDetail getUserByAccount(String account) {
        AdminUserVO adminUser = adminUserApi.getUserByMobile(account).getCheckedData();
        return AuthConvert.INSTANCE.convert(adminUser);
    }

    @Override
    public boolean isPasswordMatch(AuthUserDetail authUser, String loginPassword) {
        return adminUserApi.isPasswordMatch(authUser.getMobile(), loginPassword).getCheckedData();
    }
}
