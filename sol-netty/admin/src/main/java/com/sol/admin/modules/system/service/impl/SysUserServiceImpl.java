package com.sol.admin.modules.system.service.impl;

import com.sol.admin.common.constants.RedisKeys;
import com.sol.admin.modules.security.util.JwtUtil;
import com.sol.admin.modules.system.dto.UserInfo;
import com.sol.admin.modules.system.entity.SysUser;
import com.sol.admin.modules.system.mapper.SysUserMapper;
import com.sol.admin.modules.system.service.SysUserService;
import com.sol.admin.common.util.RedisUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author sol
 * @since 2024-02-20
 */
@Slf4j
@Service
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    RedisUtil redisUtil;
    @Autowired
    JwtUtil jwtUtil;

    @Resource
    SysUserMapper mapper;

//    @Autowired
//    AuthenticationManager authenticationManager;

    @Override
    public ResponseEntity<Map<String,Object>> sysUserLogin(ServletRequest request , String username, String password) {
        // TODO 是否要单点登录
        // 清除SecurityContextHolder中的认证信息
        SecurityContextHolder.clearContext();
        try {
            if(request instanceof StandardMultipartHttpServletRequest) {
                SecurityContextHolderAwareRequestWrapper request1;
                request1 = (SecurityContextHolderAwareRequestWrapper)
                        ((StandardMultipartHttpServletRequest) request).getRequest();

                request1.login(username, password);
            } else if (request instanceof HttpServletRequestWrapper){
                ((HttpServletRequestWrapper) request).login(username, password);
            }
        } catch (ServletException e) {
            redisUtil.incr(username,1);
            redisUtil.expire(username,60*30);
            log.error("登录错误： error message: " + e);
//            return new ResponseEntity<>("error message: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
//        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        // 生成token
        UserInfo user = mapper.getUserInfo(username);
        Map<String, Object> claims = new HashMap<>();
        claims.put("token", jwtUtil.generateToken(username));
        claims.put("tokenHead", jwtUtil.getTokenHead());
        claims.put("userInfo", user);
        // 将用户信息存入 redis
        redisUtil.hset(RedisKeys.SYS_USER_INFO, username,user);

        return new ResponseEntity<>(claims, HttpStatus.OK);
    }

    @Override
    public Boolean addUser(SysUser sysUser) {
        return mapper.insert(sysUser) == 1;
    }
}
