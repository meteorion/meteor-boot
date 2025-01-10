package pers.meteor.auth.service.token;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pers.meteor.auth.convert.AuthConvert;
import pers.meteor.auth.model.AccessToken;
import pers.meteor.auth.model.AuthUserDetail;
import pers.meteor.common.constant.CacheConstants;
import pers.meteor.common.constant.SecurityConstants;
import pers.meteor.common.utils.JwtUtils;
import pers.meteor.common.utils.uuid.IdUtils;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * @author meteor
 */
@Service
@Slf4j
public class JwtTokenServiceImpl extends AbstractTokenService {
    /**
     * 令牌有效期，默认60分钟
     */
    private final static long expireTime = CacheConstants.EXPIRATION;

    @Override
    public AccessToken createAccessToken(AuthUserDetail userDetail) {
        Map<String, Object> claimsMap = new HashMap<>();
        claimsMap.put(SecurityConstants.USER_KEY, IdUtils.fastUUID());
        claimsMap.put(SecurityConstants.DETAILS_USER_ID, userDetail.getId());
        claimsMap.put(SecurityConstants.DETAILS_USERNAME, userDetail.getMobile());

        String token = JwtUtils.createToken(claimsMap);
        AccessToken accessToken =  AuthConvert.INSTANCE.convert(userDetail);
        accessToken.setAccessToken(token);
        accessToken.setExpiresTime(LocalDateTime.now().plusSeconds(expireTime));

        // 保存token
        tokenStorgeService.saveAccessToken(accessToken, expireTime);

        return accessToken;
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
