package com.meteor.common.signature.core.interceptor;

import cn.hutool.json.JSON;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.meteor.common.signature.core.annotation.Decrypt;
import com.meteor.common.signature.core.filter.DecryptionRequestWrapper;
import org.springframework.core.MethodParameter;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author meteor
 */
public class DecryptionInterceptor implements HandlerInterceptor {

    private boolean support(HandlerMethod handlerMethod) {
        return handlerMethod.hasMethodAnnotation(Decrypt.class) || handlerMethod.getBeanType().isAnnotationPresent(Decrypt.class);
    }

    @Override
    public boolean preHandle(@NonNull HttpServletRequest request,
                             @NonNull HttpServletResponse response,
                             @NonNull Object handler) {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            if (support(handlerMethod)) {
                // 获取加密参数
                Object data = request.getAttribute("data");
                JSONObject entries = JSONUtil.parseObj(data);
                MethodParameter[] methodParameters = handlerMethod.getMethodParameters();
                for (MethodParameter methodParameter : methodParameters) {
                    String parameterName = methodParameter.getParameterName();
                    Object val = entries.get(parameterName);
                    request.setAttribute(parameterName, val);
                }
            }
        }
        return true;
    }
}
