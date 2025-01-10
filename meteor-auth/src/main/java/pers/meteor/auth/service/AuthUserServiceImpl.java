package pers.meteor.auth.service;

import pers.meteor.auth.model.AuthUserDetail;

/**
 * @author meteor
 */
public class AuthUserServiceImpl implements AuthUserService {
    @Override
    public AuthUserDetail getUserByMobile(String clientId, String mobile) {
        return null;
    }

    @Override
    public boolean isPasswordMatch(String clientId, String userPassword, String loginPassword) {
        return false;
    }
}
