package pers.meteor.common.constant;

public interface CacheConstants {

	/**
	 * 缓存有效期，默认720（分钟）
	 */
	long EXPIRATION = 720;

	/**
	 * 缓存刷新时间，默认120（分钟）
	 */
	long REFRESH_TIME = 120;

	/**
	 * 密码最大错误次数
	 */
	int PASSWORD_MAX_RETRY_COUNT = 5;

	/**
	 * 密码锁定时间，默认10（分钟）
	 */
	long PASSWORD_LOCK_TIME = 10;

	/**
	 * 权限缓存前缀
	 */
	String LOGIN_TOKEN_KEY = "login_tokens:";

	/**
	 * 刷新缓存key前缀
	 */
	String REFRSH_TOKEN_KEY = "refresh_tokens:";

	/**
	 * 验证码 redis key
	 */
	String CAPTCHA_CODE_KEY = "captcha_codes:";

	/**
	 * 参数管理 cache key
	 */
	String SYS_CONFIG_KEY = "sys_config:";

	/**
	 * 字典管理 cache key
	 */
	String SYS_DICT_KEY = "sys_dict:";

	/**
	 * 登录账户密码错误次数 redis key
	 */
	String PWD_ERR_CNT_KEY = "pwd_err_cnt:";

	/**
	 * 登录IP黑名单 cache key
	 */
	String SYS_LOGIN_BLACKIPLIST = SYS_CONFIG_KEY + "sys.login.blackIPList";

	/**
	 * 系统配置Redis-key
	 */
	String SYSTEM_CONFIG_KEY = "system:config";

	/**
	 * IP限流Redis-key
	 */
	String IP_RATE_LIMITER_KEY = "ip:rate:limiter:";

	/**
	 * 防重复提交Redis-key
	 */
	String RESUBMIT_LOCK_PREFIX = "resubmit:lock:";

	/**
	 * 单个IP请求的最大每秒查询数（QPS）阈值Key
	 */
	String IP_QPS_THRESHOLD_LIMIT_KEY = "IP_QPS_THRESHOLD_LIMIT";

	/**
	 * 手机验证码缓存前缀
	 */

	String MOBILE_VERIFICATION_CODE_PREFIX = "VERIFICATION_CODE:MOBILE:";


	/**
	 * 邮箱验证码缓存前缀
	 */
	String EMAIL_VERIFICATION_CODE_PREFIX = "VERIFICATION_CODE:EMAIL:";
}
