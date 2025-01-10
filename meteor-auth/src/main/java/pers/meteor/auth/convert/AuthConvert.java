package pers.meteor.auth.convert;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import pers.meteor.auth.dto.vo.AuthLoginVO;
import pers.meteor.auth.model.AccessToken;
import pers.meteor.auth.model.AuthUserDetail;
import pers.meteor.system.api.token.dto.vo.AccessTokenVO;

/**
 * @author meteor
 */
@Mapper
public interface AuthConvert {
    AuthConvert INSTANCE = Mappers.getMapper(AuthConvert.class);

    AuthLoginVO convert(AccessToken accessToken, String openId);

    AccessToken convert(AuthUserDetail userDetail);
}
