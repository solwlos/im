package com.sol.admin.modules.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sol.admin.modules.constants.HttpConstants;
import com.sol.admin.modules.security.util.JwtUtil;
import com.sol.admin.modules.system.entity.UserRole;
import com.sol.admin.modules.system.mapper.SysUserMapper;
import com.sol.admin.util.Result;
import jakarta.annotation.Resource;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.io.IOException;

/**
 * @author sol
 * @date 2024/1/10 14:28
 * @Version 1.0
 */
public class JwtTokenFilter extends OncePerRequestFilter {

    @Resource
    private SysUserMapper userMapper;
    @Autowired
    private JwtUtil jwtUtil;
    @Value("${myapp.jwt.tokenHeader}")
    private String tokenHeader;
    @Value("${myapp.jwt.tokenHead}")
    private String tokenHead;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {

        SecurityContextHolder.clearContext();
        String authHeader = request.getHeader(this.tokenHeader);
        try {
            if (authHeader != null && authHeader.startsWith(this.tokenHead)) {
                String authToken = authHeader.substring(this.tokenHead.length());
                String username = jwtUtil.getUserNameFromToken(authToken);
                if (username != null) {
                    // 查询数据库里面是否有该账号
                    UserRole userRole = userMapper.getUserRole(username);
                    if (userRole == null) {
                        userRole = userMapper.getUserMail(username);
                    }
                    if (userRole == null) {
                        throw new ServletException("token 无效，没有此用户！！！");
                    }
                    if (!jwtUtil.isTokenExpired(authToken)) {
                        userRole.setAuthenticated(true);
                        userRole.setAuthority(userRole.getRole());
                        UsernamePasswordAuthenticationToken
                                authentication = new UsernamePasswordAuthenticationToken(userRole.getName(), userRole.getCredentials(), userRole.getAuthorities());
                        authentication.setDetails(userRole);
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }
                }
            }
            filterChain.doFilter(request, response);
        }catch (Exception e){
            response.setContentType(HttpConstants.APPLICATION_JSON_CHARSET_UTF_8);
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.getWriter().println(HttpConstants.OBJECT_MAPPER.writeValueAsString(new ResponseEntity<>(e.getMessage(),HttpStatus.UNAUTHORIZED)));
        }
    }

}

