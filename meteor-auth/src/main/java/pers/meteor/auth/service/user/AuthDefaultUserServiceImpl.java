package pers.meteor.auth.service.user;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import pers.meteor.auth.api.dto.AuthUserDetail;
import pers.meteor.common.enums.UserTypeEnum;
import pers.meteor.common.utils.collection.SetUtils;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * @author meteor
 */
@ConditionalOnProperty(prefix = "meteor.security", name = "auth-user", havingValue = "default")
@Service
public class AuthDefaultUserServiceImpl implements AuthUserService {

    @Value("${spring.security.user.name}")
    private String username;

    @Value("${spring.security.user.password}")
    private String password;

    @Resource
    private AuthUserServiceFactory authUserServiceFactory;

    @PostConstruct
    public void registerService() {
        authUserServiceFactory.registerService(UserTypeEnum.ADMIN.getValue(), this);
    }

    @Override
    public AuthUserDetail getUserByAccount(String account) {
        return AuthUserDetail.builder()
                .id(1L)
                .mobile("15070196704")
                .userName(username)
                .roles(SetUtils.asSet("admin"))
                .accountLocked(false)
                .userType(UserTypeEnum.ADMIN.getValue())
                .clientId("1")
                .scopes(Lists.newArrayList("all"))
                .build();
    }

    @Override
    public boolean isPasswordMatch(AuthUserDetail authUser, String loginPassword) {
        return username.equals(authUser.getUserName()) && password.equals(loginPassword);
    }
}
