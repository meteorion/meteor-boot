package pers.meteor.auth.service.impl;

import pers.meteor.auth.service.AuthUserService;
import pers.meteor.common.security.core.model.AuthUserDetail;

/**
 * @author meteor
 */
public class AuthUserServiceImpl implements AuthUserService {
    @Override
    public AuthUserDetail getUserByMobile(String clientId, String mobile) {
        return null;
    }

    @Override
    public boolean isPasswordMatch(AuthUserDetail authUser, String loginPassword) {
        return false;
    }
}
