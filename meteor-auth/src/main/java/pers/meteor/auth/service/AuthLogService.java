package pers.meteor.auth.service;

import pers.meteor.common.enums.LoginLogTypeEnum;
import pers.meteor.common.enums.LoginResultEnum;
import pers.meteor.common.security.core.model.AuthUserDetail;

/**
 * 鉴权日志
 *
 * @author meteor
 */
public interface AuthLogService {

    /**
     * 保存登录记录
     *
     * @param userDetail      用户信息
     * @param loginLogType    登录类型
     * @param loginResult     登录结果
     */
    void saveLoginRecord(AuthUserDetail userDetail, LoginLogTypeEnum loginLogType, LoginResultEnum loginResult);

    /**
     * 保存登出记录
     *
     * @param userId          用户ID
     * @param loginLogTypeEnum 登出类型
     */
    void saveLogoutLog(Integer userId, LoginLogTypeEnum loginLogTypeEnum);
}
