package pers.meteor.security.core.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import pers.meteor.common.pojo.response.SingleResponse;
import pers.meteor.common.utils.ServletUtils;
import pers.meteor.security.core.utils.SecurityUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static pers.meteor.common.exception.GlobalErrorCode.FORBIDDEN;

@Slf4j
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e)
            throws IOException, ServletException {
        // 打印 warn 的原因是，不定期合并 warn，看看有没恶意破坏
        log.warn("[commence][访问 URL({}) 时，用户({}) 权限不够]", request.getRequestURI(),
                SecurityUtils.getLoginUserId(), e);
        // 返回 403
        ServletUtils.writeJSON(response, SingleResponse.error(FORBIDDEN));
    }

}
