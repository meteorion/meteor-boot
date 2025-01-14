package pers.meteor.auth.convert;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import pers.meteor.auth.dto.vo.AuthLoginVO;
import pers.meteor.security.core.model.AccessToken;
import pers.meteor.security.core.model.AuthUserDetail;
import pers.meteor.system.api.user.vo.AdminUserVO;

/**
 * @author meteor
 */
@Mapper
public interface AuthConvert {
    AuthConvert INSTANCE = Mappers.getMapper(AuthConvert.class);

    AuthLoginVO convert(AccessToken accessToken, String openId);

    AuthUserDetail convert(AdminUserVO adminUser);
}
