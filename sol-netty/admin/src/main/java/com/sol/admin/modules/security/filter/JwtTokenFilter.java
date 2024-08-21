package com.sol.admin.modules.security.filter;

import com.sol.admin.common.constants.HttpConstants;
import com.sol.admin.common.constants.RedisKeys;
import com.sol.admin.modules.security.util.JwtUtil;
import com.sol.admin.modules.system.dto.UserRole;
import com.sol.admin.modules.system.mapper.SysUserMapper;
import com.sol.admin.common.util.RedisUtil;
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

import java.io.IOException;
import java.util.Map;

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

    @Autowired
    RedisUtil redisUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        // 线程在线程池中重复利用，需要清楚当前线程的安全上下文
        SecurityContextHolder.clearContext();
        String authHeader = request.getHeader(this.tokenHeader);
        try {
            if (authHeader != null && authHeader.startsWith(this.tokenHead)) {
                String authToken = authHeader.substring(this.tokenHead.length());
                String username = jwtUtil.getUserNameFromToken(authToken);
                // 从redis中获取用户信息
                Map<Object,Object> map = redisUtil.hmget(RedisKeys.SYS_USER_INFO);
                if (map == null || !map.containsKey(username)){
                    throw new ServletException("请重新登录 ！！！");
                }
                // 检测时间是否到期
                if (!jwtUtil.isTokenExpired(authToken)) {
                    UserRole user = (UserRole)map.get(username);
                    user.setAuthenticated(true);
                    user.setAuthority(user.getRole());
                    UsernamePasswordAuthenticationToken
                            authentication = new UsernamePasswordAuthenticationToken(user.getName(), user.getCredentials(), user.getAuthorities());
                    authentication.setDetails(user);
                    // 设置当前线程的安全上下文
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
            filterChain.doFilter(request, response);
        }catch (Exception e){
            response.setContentType(HttpConstants.APPLICATION_JSON_CHARSET_UTF_8);
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.getWriter().println(HttpConstants.OBJECT_MAPPER.writeValueAsString(new ResponseEntity<>(e.getMessage(),HttpStatus.UNAUTHORIZED)));
        }
    }

//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
//
//        SecurityContextHolder.clearContext();
//        String authHeader = request.getHeader(this.tokenHeader);
//        try {
//            if (authHeader != null && authHeader.startsWith(this.tokenHead)) {
//                String authToken = authHeader.substring(this.tokenHead.length());
//                String username = jwtUtil.getUserNameFromToken(authToken);
//                if (username != null) {
//                    // 查询数据库里面是否有该账号
//                    UserRole userRole = userMapper.getUserRole(username);
//                    if (userRole == null) {
//                        userRole = userMapper.getUserMail(username);
//                    }
//                    if (userRole == null) {
//                        throw new ServletException("token 无效，没有此用户！！！");
//                    }
//                    if (!jwtUtil.isTokenExpired(authToken)) {
//                        userRole.setAuthenticated(true);
//                        userRole.setAuthority(userRole.getRole());
//                        UsernamePasswordAuthenticationToken
//                                authentication = new UsernamePasswordAuthenticationToken(userRole.getName(), userRole.getCredentials(), userRole.getAuthorities());
//                        authentication.setDetails(userRole);
//                        SecurityContextHolder.getContext().setAuthentication(authentication);
//                    }
//                }
//            }
//            filterChain.doFilter(request, response);
//        }catch (Exception e){
//            response.setContentType(HttpConstants.APPLICATION_JSON_CHARSET_UTF_8);
//            response.setStatus(HttpStatus.UNAUTHORIZED.value());
//            response.getWriter().println(HttpConstants.OBJECT_MAPPER.writeValueAsString(new ResponseEntity<>(e.getMessage(),HttpStatus.UNAUTHORIZED)));
//        }
//    }

}

