package pers.meteor.auth.enums;

import pers.meteor.common.constant.RpcConstants;

/**
 * API 相关的枚举
 *
 * @author meteor
 */
public class ApiConstants {

    /**
     * 服务名
     *
     * 注意，需要保证和 spring.application.name 保持一致
     */
    public static final String NAME = "auth-server";

    public static final String PREFIX = RpcConstants.RPC_API_PREFIX + "/auth";

    public static final String VERSION = "1.0.0";

}
