package com.sol.admin.modules.system.service.impl;

import com.sol.admin.modules.common.constants.RedisKeys;
import com.sol.admin.modules.security.util.JwtUtil;
import com.sol.admin.modules.system.dto.UserRole;
import com.sol.admin.modules.system.entity.SysUser;
import com.sol.admin.modules.system.mapper.SysUserMapper;
import com.sol.admin.modules.system.service.SysUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sol.admin.util.RedisUtil;
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
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Autowired
    RedisUtil redisUtil;
    @Autowired
    JwtUtil jwtUtil;

    @Resource
    SysUserMapper mapper;

    @Autowired
    AuthenticationManager authenticationManager;

    @Override
    public ResponseEntity<?> sysUserLogin(ServletRequest request , String username, String password) {
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
            return new ResponseEntity<>("error message: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
//        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        // 生成token
        Map<String, String> claims = new HashMap<>();
        claims.put("token", jwtUtil.generateToken(username));
        claims.put("tokenHead", jwtUtil.getTokenHead());
        // 将用户信息存入 redis
        Map<String,Object> map = new HashMap<>();
//        map.put(username,mapper.getLoginName(username));
        map.put(username,mapper.getUserRole(username));
        redisUtil.hmset(RedisKeys.SYS_USER_INFO, map);

        return new ResponseEntity<>(claims, HttpStatus.OK);
    }
}
