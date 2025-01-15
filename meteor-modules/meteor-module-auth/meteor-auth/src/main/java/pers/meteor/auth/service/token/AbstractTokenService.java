package pers.meteor.auth.service.token;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import pers.meteor.auth.api.dto.AccessToken;

/**
 * @author meteor
 */
@Setter
@Slf4j
public abstract class AbstractTokenService implements TokenService {

    protected TokenStorgeService tokenStorgeService;

    @Override
    public AccessToken getAccessToken(String accessToken) {
        return tokenStorgeService.getAccessToken(accessToken);
    }

    @Override
    public AccessToken removeAccessToken(String accessToken) {
        return tokenStorgeService.removeAccessToken(accessToken);
    }
}
