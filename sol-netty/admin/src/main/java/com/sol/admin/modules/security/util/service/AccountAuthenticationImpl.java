package com.sol.admin.modules.security.util.service;

import com.sol.admin.common.constants.RedisKeys;
import com.sol.admin.common.util.RedisUtil;
import com.sol.admin.modules.system.entity.SysUser;
import com.sol.admin.modules.system.dto.UserRole;
import com.sol.admin.modules.system.mapper.SysUserMapper;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;


@Service
@Slf4j
public class AccountAuthenticationImpl implements AuthenticationProvider {

    @Resource
    SysUserMapper userMapper;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    HttpServletRequest request;

    /**
     * 账号 登录
     * @param authentication the authentication request object.
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        //用户传入的信息
        UserRole userRole = new UserRole(
                authentication.getName(), //登录名
                (String) authentication.getCredentials());//密码

        //通过用户名获取用户信息
        SysUser user = userMapper.getLoginName(userRole.getName());
        //查询数据库中是否有该用户
        if (user == null) throw new UsernameNotFoundException("username not found exception");

        String password = user.getPassword();//查询数据库中该用户密码
        if(!passwordEncoder.matches((String) authentication.getCredentials(),password)){//比较密码
            throw new UsernameNotFoundException("password not right exception");
        }

        //用户的角色，到时候要加
        userRole.setAuthority(user.getRoleId()+"");
        //表示获得授权
        userRole.setAuthenticated(true);
        return userRole;
    }
    /**
     * AuthenticationManager 本身不包含认证逻辑，其核心是用来管理所有的 AuthenticationProvider，通过交由合适的 AuthenticationProvider 来实现认证。
     * ProviderManager 是AuthenticationProvider的实现类
     * AuthenticationManager获取所有AuthenticationProvider的实现
     * 通过该方法判断是否支持当前方式的认证
     * 这里支持验证UsernamePasswordAuthenticationToken
     */
    @Override
    public boolean supports(Class<?> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }
}
