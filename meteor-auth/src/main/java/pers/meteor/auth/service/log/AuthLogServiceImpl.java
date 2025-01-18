package pers.meteor.auth.service.log;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pers.meteor.auth.api.dto.AuthUserDetail;
import pers.meteor.common.enums.LoginLogTypeEnum;
import pers.meteor.common.enums.LoginResultEnum;

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
    public void saveLogoutLog(Long userId, LoginLogTypeEnum loginLogType) {
        log.info("登出类型：{}", loginLogType);
    }
}
