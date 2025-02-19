package pers.meteor.security.core.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import pers.meteor.auth.api.user.RemoteUserService;
import pers.meteor.auth.api.user.vo.AdminUserVO;
import pers.meteor.common.constant.CacheConstants;
import pers.meteor.common.entity.R;
import pers.meteor.security.core.entity.AuthUser;

import java.util.Objects;

/**
 * 用户详细信息
 *
 * @author lengleng hccake
 */
@Slf4j
@Primary
@RequiredArgsConstructor
public class AdminUserDetailsServiceImpl implements AdminUserDetailsService {

	private final RemoteUserService remoteUserService;

	private final CacheManager cacheManager;

	/**
	 * 用户名密码登录
	 * @param username 用户名
	 * @return
	 */
	@Override
	@SneakyThrows
	public UserDetails loadUserByUsername(String username) {
		Cache cache = cacheManager.getCache(CacheConstants.USER_DETAILS);
		if (cache != null && cache.get(username) != null) {
			return (AuthUser) Objects.requireNonNull(cache.get(username)).get();
		}

		R<AdminUserVO> result = remoteUserService.getUserByUsername(username);
		UserDetails userDetails = getUserDetails(result);
		if (cache != null) {
			cache.put(username, userDetails);
		}
		return userDetails;
	}

	@Override
	public int getOrder() {
		return Integer.MIN_VALUE;
	}

}
