package pers.meteor.auth.service.user;

import org.springframework.stereotype.Service;
import pers.meteor.common.exception.ServiceException;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 鉴权用户服务工厂实现类
 *
 * @author 钟宗兵
 * @since 1.0.0
 */
@Service
public class AuthUserServiceFactoryImpl implements AuthUserServiceFactory {

    private final ConcurrentHashMap<Integer, AuthUserService> authUserServiceMap = new ConcurrentHashMap<>();

    @Override
    public void registerService(Integer userType, AuthUserService authUserService) {
        if (authUserServiceMap.containsKey(userType)) {
            throw new ServiceException("鉴权服务已注册");
        }
        authUserServiceMap.put(userType, authUserService);
    }

    @Override
    public AuthUserService getUserService(Integer userType) {
        AuthUserService authUserService = authUserServiceMap.get(userType);
        if (authUserService == null) {
            throw new ServiceException("鉴权服务未注册");
        }
        return authUserService;
    }
}
