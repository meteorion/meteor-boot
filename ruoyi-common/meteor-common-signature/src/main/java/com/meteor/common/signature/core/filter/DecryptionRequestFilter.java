package com.meteor.common.signature.core.filter;

import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.meteor.common.signature.core.properties.DecryptProperties;
import com.meteor.common.signature.core.service.DecryptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author meteor
 */
@RequiredArgsConstructor
public class DecryptionRequestFilter extends OncePerRequestFilter {
    private final DecryptionService decryptionService;

    @Override
    protected boolean shouldNotFilter(@NonNull HttpServletRequest request) throws ServletException {
        return !decryptionService.support(request);
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        // 创建一个RequestWrapper对象，将传入的HttpServletRequest对象作为参数
        RequestWrapper requestWrapper = new RequestWrapper(request);
        // 获取请求体内容
        String body = requestWrapper.getBody();
        // 解析请求体为JSONObject对象
        JSONObject entries = JSONUtil.parseObj(body);
        // 获取解密属性配置
        DecryptProperties decryptProperties = decryptionService.getDecryptProperties();
        // 获取指定字段的加密数据
        String encryptData = entries.getStr(decryptProperties.getFiled());
        if (CharSequenceUtil.isNotBlank(encryptData)) {
            // 对加密数据进行解密操作
            String decrypt = decryptionService.decrypt(encryptData, requestWrapper);
            // 将解密后的数据设置为请求体内容
            requestWrapper.setBody(decrypt);
        }
        // 执行下一个过滤器的过滤操作，传入解密后的请求Wrapper对象和响应对象
        filterChain.doFilter(requestWrapper, response);
    }
}
