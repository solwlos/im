package com.sol.admin.modules.security.service;


import com.sol.admin.modules.system.entity.SysUser;
import com.sol.admin.modules.system.entity.UserRole;
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
import com.sol.admin.util.RedisUtil;

/**
 * @author sol
 * @date 2024/1/10 10:58
 * @Version 1.0
 */
@Service
@Slf4j
public class MailAuthenticationImpl implements AuthenticationProvider {

    @Resource
    SysUserMapper userMapper;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Resource
    RedisUtil redisUtil;
    @Autowired
    HttpServletRequest request;

    /**
     * 邮箱 登录
     * @param authentication the authentication request object.
     * @return
     * @throws AuthenticationException
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        //用户传入的信息
        UserRole userRole = new UserRole(
                authentication.getName(), //邮箱
                (String) authentication.getCredentials());//密码

        //联系方式手机号、邮箱等
        String mailNumber = authentication.getName();
        //系统生成的授权码
        String authenticationCode = (String) authentication.getCredentials();
        //通过用户名获取用户信息
        SysUser user = userMapper.getLoginName(userRole.getName());//查询数据库中是否有该用户邮箱

        if (user == null) {
            throw new UsernameNotFoundException("username.not.found.exception");
        }
        String redisKey = mailNumber + "authorizationCode";

        if(redisUtil.hasKey(redisKey)){
            if (!authenticationCode.equals(redisUtil.get(redisKey)) ) {
                throw new UsernameNotFoundException("authorizationCode.failed");
            }
        }else {
            String password = user.getPassword();
            if(!passwordEncoder.matches((String) authentication.getCredentials(),password)){
                throw new UsernameNotFoundException("password.not.right.exception");
            }
        }
        userRole.setAuthority(user.getRoleId() + "");
        userRole.setAuthenticated(true);
        return userRole;
    }
    /**
     * AuthenticationManager 本身不包含认证逻辑，其核心是用来管理所有的 AuthenticationProvider，通过交由合适的 AuthenticationProvider 来实现认证。
     * ProviderManager 是AuthenticationProvider的实现类
     * AuthenticationManager获取所有AuthenticationProvider的实现
     * 通过该方法判断是否支持当前方式的认证
     * 这里支持验证 UsernamePasswordAuthenticationToken
     * @param authentication
     * @return
     */
    @Override
    public boolean supports(Class<?> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }
}
