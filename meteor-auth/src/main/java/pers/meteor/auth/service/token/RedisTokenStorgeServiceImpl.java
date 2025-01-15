package pers.meteor.auth.service.token;

import org.springframework.stereotype.Service;
import pers.meteor.auth.api.dto.AccessToken;
import pers.meteor.auth.api.dto.RefreshToken;
import pers.meteor.common.constant.CacheConstants;
import pers.meteor.common.redis.service.RedisService;
import pers.meteor.common.utils.StringUtils;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @author meteor
 */
@Service
public class RedisTokenStorgeServiceImpl implements TokenStorgeService {

    @Resource
    private RedisService redisService;

    /**
     * 令牌自定义标识
     */
    private final static String ACCESS_TOKEN = CacheConstants.LOGIN_TOKEN_KEY;

    /**
     * 刷新令牌标识
     */
    private final static String REFRESH_TOKEN = CacheConstants.REFRSH_TOKEN_KEY;

    @Override
    public void saveAccessToken(AccessToken accessToken, long expires) {
        // 根据uuid将loginUser缓存
        String tokenKey = getAccessTokenKey(accessToken.getAccessToken());
        redisService.setCacheObject(tokenKey, accessToken, expires, TimeUnit.MINUTES);
    }

    @Override
    public void saveRefreshToken(RefreshToken refreshToken, long expires) {
        redisService.setCacheObject(getRefreshTokenKey(refreshToken.getRefreshToken()), refreshToken, expires, TimeUnit.MINUTES);
    }

    @Override
    public AccessToken removeAccessToken(String token) {
        if (StringUtils.isNotEmpty(token)) {
            AccessToken accessToken = getAccessToken(token);
            if (accessToken != null) {
                redisService.deleteObject(getAccessTokenKey(accessToken.getAccessToken()));
            }
            return accessToken;
        }
        return null;
    }

    @Override
    public RefreshToken removeRefreshToken(String token) {
        if (StringUtils.isNotEmpty(token)) {
            RefreshToken refreshToken =  getRefreshToken(token);
            if (refreshToken != null) {
                redisService.deleteObject(getRefreshTokenKey(refreshToken.getRefreshToken()));
            }
            return refreshToken;
        }
        return null;
    }

    @Override
    public AccessToken getAccessToken(String token) {
        String tokenKey = getAccessTokenKey(token);
        AccessToken accessToken = redisService.getCacheObject(tokenKey);
        if (accessToken != null) {
            return accessToken;
        }
        accessToken = new AccessToken();
        accessToken.setAccessToken(token);
        return accessToken;
    }

    @Override
    public RefreshToken getRefreshToken(String token) {
        return redisService.getCacheObject(getRefreshTokenKey(token));
    }

    private String getAccessTokenKey(String token) {
        return ACCESS_TOKEN + token;
    }

    private String getRefreshTokenKey(String token) {
        return REFRESH_TOKEN + token;
    }
}
