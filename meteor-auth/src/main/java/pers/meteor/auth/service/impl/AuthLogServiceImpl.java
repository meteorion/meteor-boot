package pers.meteor.auth.service.impl;

import org.springframework.stereotype.Service;
import pers.meteor.auth.service.AuthLogService;
import pers.meteor.common.enums.LoginLogTypeEnum;
import pers.meteor.common.enums.LoginResultEnum;
import pers.meteor.common.security.core.model.AuthUserDetail;

/**
 * @author meteor
 */
@Service
public class AuthLogServiceImpl implements AuthLogService {

    @Override
    public void saveLoginRecord(AuthUserDetail userDetail, LoginLogTypeEnum loginLogType, LoginResultEnum loginResult) {

    }

    @Override
    public void saveLogoutLog(Integer userId, LoginLogTypeEnum loginLogTypeEnum) {

    }
}
