package pers.meteor.auth.service.token;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pers.meteor.auth.model.AccessToken;
import pers.meteor.system.api.token.AccessTokenManagerApi;

import javax.annotation.Resource;

/**
 * 自定义Token存储服务
 * @author meteor
 */
@Service
@Slf4j
public class CustomeTokenStorgeServiceImpl implements TokenStorgeService {

    @Resource
    private AccessTokenManagerApi accessTokenManagerApi;

    @Override
    public void saveAccessToken(pers.meteor.auth.model.AccessToken accessToken) {

    }

    @Override
    public AccessToken removeAccessToken(String token) {
        return null;
    }

    @Override
    public AccessToken getAccessToken(String token) {
        return null;
    }
}
