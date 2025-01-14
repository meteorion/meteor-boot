package pers.meteor.auth.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pers.meteor.auth.service.AuthLogService;
import pers.meteor.common.enums.LoginLogTypeEnum;
import pers.meteor.common.enums.LoginResultEnum;
import pers.meteor.security.core.model.AuthUserDetail;

/**
 * @author meteor
 */
@Service
@Slf4j
public class AuthLogServiceImpl implements AuthLogService {

    @Override
    public void saveLoginRecord(AuthUserDetail userDetail, LoginLogTypeEnum loginLogType, LoginResultEnum loginResult) {
        log.info("登录结果：{}", loginResult);
    }

    @Override
    public void saveLogoutLog(Integer userId, LoginLogTypeEnum loginLogType) {
        log.info("登出类型：{}", loginLogType);
    }
}
