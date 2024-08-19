package com.sol.admin.modules.security.handler;

import com.sol.admin.modules.common.constants.HttpConstants;
import com.sol.admin.util.Result;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;

/**
 * @author sol
 * @date 2023/12/22 16:33
 * @Version 1.0
 */
public class MyAuthenticationHandler implements AuthenticationEntryPoint,AccessDeniedHandler {

    /**
     * 认证失败处理 登录失败
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException {
        String detailMessage = e.getClass().getSimpleName() + " " + e.getLocalizedMessage();
        response.setContentType(HttpConstants.APPLICATION_JSON_CHARSET_UTF_8);
        if (response.getStatus() == 404){
            response.setStatus(HttpStatus.NOT_FOUND.value());
            response.getWriter().println(HttpConstants.OBJECT_MAPPER.writeValueAsString(Result.error("系统找不到路径。")));
        }else {
            if (e instanceof InsufficientAuthenticationException) {
                detailMessage = "请登录后重试。";
            }
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.getWriter().println(HttpConstants.OBJECT_MAPPER.writeValueAsString(new ResponseEntity<>("登录过期，"+detailMessage,HttpStatus.UNAUTHORIZED)));
        }

    }

    /**
     * 权限不足时的处理
     */
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
        response.setContentType(HttpConstants.APPLICATION_JSON_CHARSET_UTF_8);
        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.getWriter().println(HttpConstants.OBJECT_MAPPER.writeValueAsString(new ResponseEntity<>("权限不足",HttpStatus.FORBIDDEN)));
    }

}
