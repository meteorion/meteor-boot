package pers.meteor.auth.convert;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import pers.meteor.auth.dto.vo.AuthLoginVO;
import pers.meteor.common.security.core.model.AccessToken;
import pers.meteor.common.security.core.model.AuthUserDetail;
import pers.meteor.common.security.core.model.RefreshToken;

/**
 * @author meteor
 */
@Mapper
public interface AuthConvert {
    AuthConvert INSTANCE = Mappers.getMapper(AuthConvert.class);

    AuthLoginVO convert(AccessToken accessToken, String openId);

    AccessToken convert(RefreshToken refreshToken);

    RefreshToken convertRefreshToken(AuthUserDetail userDetail);
}
