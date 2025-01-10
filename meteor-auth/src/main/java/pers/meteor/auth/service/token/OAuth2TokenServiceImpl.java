package pers.meteor.auth.service.token;

import pers.meteor.auth.model.AccessToken;
import pers.meteor.auth.model.AuthUserDetail;

/**
 * @author meteor
 */
public class OAuth2TokenServiceImpl extends AbstractTokenService {
    @Override
    public AccessToken createAccessToken(AuthUserDetail userDetail) {
        return null;
    }

    @Override
    public AccessToken refreshAccessToken(String refreshToken, String clientId) {
        return null;
    }

    @Override
    public AccessToken checkAccessToken(String accessToken) {
        return null;
    }
}
