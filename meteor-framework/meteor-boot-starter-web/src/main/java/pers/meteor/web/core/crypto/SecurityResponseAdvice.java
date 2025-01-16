package pers.meteor.web.core.crypto;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import pers.meteor.common.pojo.response.SingleResponse;
import pers.meteor.web.core.annotation.SecurityApi;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;


@Slf4j
@ControllerAdvice
public class SecurityResponseAdvice implements ResponseBodyAdvice<Object> {
    @Resource
    private CryptoService cryptoService;

    @Override
    public boolean supports(@NonNull MethodParameter returnType, @NonNull Class converterType) {
        Method method = returnType.getMethod();
        assert method != null;
        if(method.isAnnotationPresent(SecurityApi.class)) {
            return true;
        }
        Class<?> aClass = method.getDeclaringClass();
        while (Object.class != aClass) {
            if (aClass.isAnnotationPresent(SecurityApi.class)) {
                return true;
            }
            aClass = aClass.getSuperclass();
        }
        return false;
    }

    @Override
    public Object beforeBodyWrite(Object body, @NonNull MethodParameter returnType, @NonNull MediaType selectedContentType,
                                  @NonNull Class selectedConverterType, @NonNull ServerHttpRequest request, @NonNull ServerHttpResponse response) {

        SingleResponse<Object> apiResponse = SingleResponse.success(null);
        if (body != null) {
            if (body instanceof SingleResponse) {
                apiResponse = (SingleResponse<Object>) body;
            } else {
                apiResponse.setData(String.valueOf(body));
            }
        }

        HttpServletRequest servletRequest = ((ServletServerHttpRequest) request).getServletRequest();
        String agentId = (String) servletRequest.getAttribute("agentId");

        Object data = apiResponse.getData();
        if (StrUtil.isNotBlank(agentId) && data != null) {
            CryptoConfig agentConfig = cryptoService.getCryptoConfig(agentId);
            String encrypt = cryptoService.encrypt(String.valueOf(data), agentConfig.getEncryptKey());
            apiResponse.setData(encrypt);
        }

        return apiResponse;
    }

}
