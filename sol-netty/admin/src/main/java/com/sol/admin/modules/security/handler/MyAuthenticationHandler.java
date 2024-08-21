package com.sol.admin.modules.security.handler;

import com.sol.admin.common.util.Result;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.csrf.CsrfException;
import org.springframework.security.web.csrf.InvalidCsrfTokenException;
import org.springframework.security.web.csrf.MissingCsrfTokenException;

import java.io.IOException;

import static com.sol.admin.common.constants.HttpConstants.APPLICATION_JSON_CHARSET_UTF_8;
import static com.sol.admin.common.constants.HttpConstants.OBJECT_MAPPER;

/**
 * @author sol
 * @date 2023/12/22 16:33
 * @Version 1.0
 */
public class MyAuthenticationHandler implements AuthenticationEntryPoint,AccessDeniedHandler {

    /**
     * 认证失败处理 登录失败、
     * @param request       that resulted in an <code>AuthenticationException</code>
     * @param response      so that the user agent can begin authentication
     * @throws AuthenticationException      异常
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException {
        String detailMessage = e.getClass().getSimpleName() + " " + e.getLocalizedMessage();

        response.setContentType(APPLICATION_JSON_CHARSET_UTF_8);
        if (response.getStatus() == 404){
            response.setStatus(HttpStatus.NOT_FOUND.value());
            response.getWriter().println(OBJECT_MAPPER.writeValueAsString(Result.error("系统找不到路径")));
        }else {
//            if (e instanceof InsufficientAuthenticationException) {
//                detailMessage = e.getMessage();
//            }
            response.setStatus(HttpStatus.FORBIDDEN.value());
            response.getWriter().println(OBJECT_MAPPER.writeValueAsString(Result.error("Insufficient authority."+detailMessage)));
        }

    }

    /**
     * 权限不足时的处理
     * @param request               that resulted in an <code>AccessDeniedException</code>
     * @param response              so that the user agent can be advised of the failure
     * @param accessDeniedException that caused the invocation
     */
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
        String detailMessage = "";
        if (accessDeniedException instanceof MissingCsrfTokenException) {
            detailMessage = "Missing CSRF TOKEN, pass it in from form or HEADER.";
        } else if (accessDeniedException instanceof InvalidCsrfTokenException) {
            detailMessage = "Invalid CSRF TOKEN.";
        } else if (accessDeniedException instanceof CsrfException) {
            detailMessage = accessDeniedException.getLocalizedMessage();
        } else if (accessDeniedException instanceof AuthorizationServiceException) {
            detailMessage = AuthorizationServiceException.class.getSimpleName() + " " + accessDeniedException.getLocalizedMessage();
        } else{
            detailMessage = accessDeniedException.getMessage();
        }

        response.setContentType(APPLICATION_JSON_CHARSET_UTF_8);
        if (response.getStatus() == 404){
            response.setStatus(HttpStatus.NOT_FOUND.value());
            response.getWriter().println(OBJECT_MAPPER.writeValueAsString(Result.error("系统找不到路径")));
        }else {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.getWriter().println(OBJECT_MAPPER.writeValueAsString(Result.error("Login expiration. " + detailMessage)));
        }
    }

}
