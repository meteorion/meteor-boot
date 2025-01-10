package pers.meteor.common.security.crypto;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import pers.meteor.common.exception.ServiceException;
import pers.meteor.common.security.annotation.SecurityApi;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.lang.NonNull;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 钟宗兵
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
public class SecurityApiInterceptor implements HandlerInterceptor {
    private final CryptoService cryptoService;

    /**
     * 获取指定注解
     *
     * @param method          /
     * @param annotationClass /
     * @return /
     */
    public static SecurityApi getClassAnnotation(HandlerMethod method, Class<? extends SecurityApi> annotationClass) {
        Class<?> clazz = method.getBeanType();
        while (Object.class != clazz) {
            if (clazz.isAnnotationPresent(annotationClass)) {
                return clazz.getAnnotation(annotationClass);
            }
            clazz = clazz.getSuperclass();
        }
        return null;
    }

    @Override
    public boolean preHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler) {
        // 如果不是映射到方法直接通过
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        SecurityApi securityApi = handlerMethod.getMethodAnnotation(SecurityApi.class);
        if (securityApi == null) {
            securityApi = getClassAnnotation(handlerMethod, SecurityApi.class);
        }
        // 如果没有用加密注解或不加密， 就直接放行。
        if (securityApi == null || !securityApi.inDecode()) {
            return true;
        }
        HttpMethod httpMethod = HttpMethod.resolve(request.getMethod());
        //  拦截post请求
        if (!HttpMethod.POST.equals(httpMethod)) {
            return true;
        }
        //如果类型是空的就放行
        if (request.getContentType() == null) {
            return true;
        }

        //到这只解析有加密要求的注解和 POST请求中是json。不符合以上的直接不涉及数据加密
        RequestWrapper requestWrapper = (RequestWrapper)request;
        String jsonBody = requestWrapper.getBody();
        log.info("请求原参：{}", jsonBody);
        SecurityApiRequest securityApiRequest = JSON.parseObject(jsonBody, SecurityApiRequest.class);
        if (StrUtil.isBlank(securityApiRequest.getData())) {
            return true;
        }
        // 获取代理参数
        String agentId = securityApiRequest.getAgentId();
        CryptoConfig agentConfig = cryptoService.getCryptoConfig(agentId);
        if (agentConfig == null) {
            throw new ServiceException("获取解密参数失败");
        }
        request.setAttribute("agentId", agentId);
        // 解密
        String requestParams;
        try {
            requestParams = cryptoService.decrypt(securityApiRequest.getData(), agentConfig.getDecryptKey());
        } catch (Exception e) {
            log.error("解密失败", e);
            throw new ServiceException("解密失败");
        }
        log.info("解密结果：{}", requestParams);
        // 验签
        JSONObject jsonObject = JSON.parseObject(requestParams);
        String signature = jsonObject.getString("signature");
        boolean compareSignature = cryptoService.compareSignature(jsonObject, agentConfig.getSignKey(), signature);
        if (!compareSignature) {
            throw new ServiceException("验签失败");
        }
        requestWrapper.setBody(requestParams);

        return true;
    }

    @Override
    public void postHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler, Exception ex) throws Exception {

    }
}
