package pers.meteor.auth.service.log;

import pers.meteor.auth.api.dto.AuthUserDetail;
import pers.meteor.common.enums.LoginLogTypeEnum;
import pers.meteor.common.enums.LoginResultEnum;

/**
 * 鉴权日志
 *
 * @author meteor
 */
public interface AuthLogService {

    /**
     * 保存登录记录
     * @param userId          用户ID
     * @param account         登录账号
     * @param loginLogType 登录类型
     * @param loginResult     登录结果
     */
    void saveLoginRecord(Long userId, String account, LoginLogTypeEnum loginLogType, LoginResultEnum loginResult);

    /**
     * 保存登出记录
     *
     * @param userId          用户ID
     * @param loginLogType 登出类型
     */
    void saveLogoutLog(Long userId, LoginLogTypeEnum loginLogType);
}
