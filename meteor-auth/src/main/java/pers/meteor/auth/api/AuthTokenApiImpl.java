package pers.meteor.auth.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;
import pers.meteor.auth.api.dto.AccessToken;
import pers.meteor.auth.api.dto.AuthUserDetail;
import pers.meteor.auth.service.token.TokenService;
import pers.meteor.common.pojo.response.SingleResponse;

/**
 * @author meteor
 */
@RestController
@Slf4j
@RequiredArgsConstructor
public class AuthTokenApiImpl implements AuthTokenApi {

    private final TokenService tokenService;

    @Override
    public SingleResponse<AccessToken> createAccessToken(AuthUserDetail userDetail) {
        return SingleResponse.success(tokenService.createAccessToken(userDetail));
    }

    @Override
    public SingleResponse<AccessToken> refreshAccessToken(String token, String clientId) {
        return SingleResponse.success(tokenService.refreshAccessToken(token, clientId));
    }

    @Override
    public SingleResponse<AccessToken> checkAccessToken(String accessToken) {
        return SingleResponse.success(tokenService.checkAccessToken(accessToken));
    }

    @Override
    public SingleResponse<AccessToken> removeAccessToken(String accessToken) {
        return SingleResponse.success(tokenService.removeAccessToken(accessToken));
    }
}
