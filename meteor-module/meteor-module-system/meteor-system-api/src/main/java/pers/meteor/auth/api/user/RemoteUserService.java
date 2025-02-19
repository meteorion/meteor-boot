package pers.meteor.auth.api.user;

import pers.meteor.auth.api.user.vo.AdminUserVO;
import pers.meteor.common.entity.R;

/**
 * @author meteor
 */
public interface RemoteUserService {

    R<AdminUserVO> getUserByUsername(String username);
}
