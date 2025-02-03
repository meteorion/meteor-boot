package pers.meteor.auth.convert;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import pers.meteor.auth.api.dto.AccessToken;
import pers.meteor.auth.api.dto.AccessTokenCheckResult;
import pers.meteor.auth.api.dto.AuthUserDetail;
import pers.meteor.auth.model.auth.vo.AuthLoginVO;
import pers.meteor.system.api.user.vo.AdminUserVO;

/**
 * @author meteor
 */
@Mapper
public interface AuthConvert {
    AuthConvert INSTANCE = Mappers.getMapper(AuthConvert.class);

    AuthUserDetail convert(AdminUserVO adminUser);

    AccessTokenCheckResult convert(AccessToken accessToken);

    default AuthLoginVO convert(AccessToken accessToken, String openId) {
        if ( accessToken == null) {
            return null;
        }

        AuthLoginVO authLoginVO = new AuthLoginVO();
        authLoginVO.setUserId(accessToken.getUserId());
        authLoginVO.setAccessToken(accessToken.getAccessToken());
        authLoginVO.setRefreshToken(accessToken.getRefreshToken());
        authLoginVO.setExpiresTime(accessToken.getExpiresTime());
        authLoginVO.setOpenId(openId);

        return authLoginVO;
    }
}
