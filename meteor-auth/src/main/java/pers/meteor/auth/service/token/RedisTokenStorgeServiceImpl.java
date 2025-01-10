package pers.meteor.auth.service.token;

import org.springframework.stereotype.Service;
import pers.meteor.auth.convert.AuthConvert;
import pers.meteor.auth.model.AccessToken;
import pers.meteor.common.constant.CacheConstants;
import pers.meteor.common.redis.service.RedisService;
import pers.meteor.common.utils.JwtUtils;
import pers.meteor.common.utils.StringUtils;
import pers.meteor.system.api.token.dto.vo.AccessTokenVO;

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
     * 毫秒数
     */
    protected static final long MILLIS_SECOND = 1000;
    /**
     * 一分钟
     */
    protected static final long MILLIS_MINUTE = 60 * MILLIS_SECOND;
    /**
     * 令牌自定义标识
     */
    private final static String ACCESS_TOKEN = CacheConstants.LOGIN_TOKEN_KEY;
    /**
     * 缓存刷新时间
     */
    private final static Long MILLIS_MINUTE_TEN = CacheConstants.REFRESH_TIME * MILLIS_MINUTE;

    @Override
    public void saveAccessToken(AccessToken accessToken, long expires) {
        // 根据uuid将loginUser缓存
        String tokenKey = getTokenKey(accessToken.getAccessToken());
        redisService.setCacheObject(tokenKey, accessToken, expires, TimeUnit.MINUTES);
    }

    @Override
    public AccessToken removeAccessToken(String token) {
        if (StringUtils.isNotEmpty(token)) {
            AccessToken accessToken = getAccessToken(token);
            redisService.deleteObject(getTokenKey(accessToken.getAccessToken()));
            return accessToken;
        }
        return new AccessToken();
    }

    @Override
    public AccessToken getAccessToken(String token) {
        String userToken = JwtUtils.getUserKey(token);
        String tokenKey = getTokenKey(userToken);
        AccessToken accessToken = redisService.getCacheObject(tokenKey);
        if (accessToken != null) {
            return accessToken;
        }
        AccessToken accessTokenVo = new AccessToken();
        accessToken.setAccessToken(userToken);
        return accessTokenVo;
    }

    private String getTokenKey(String token)
    {
        return ACCESS_TOKEN + token;
    }
}
