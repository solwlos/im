package com.sol.admin.modules.security.service;

import com.sol.admin.common.util.RedisUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * @author sol
 * @date 2024/1/10 14:40
 * @Version 1.0
 */
@Slf4j
@Service
public class MenuAccessDecisionManager implements AccessDecisionManager {

//    @Resource
//    RolePermissionMapper rolePermissionMapper;
    @Autowired
    HttpServletResponse response;
    @Resource
    RedisUtil redisUtil;
    /**
     * 对用户权限进行匹配
     * @param authentication the caller invoking the method (not null)
     * @param object the secured object being called
     * @param collection the configuration attributes associated with the secured
     * object being invoked
     * @throws AccessDeniedException
     * @throws InsufficientAuthenticationException
     */
    @Override
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> collection) throws AccessDeniedException, InsufficientAuthenticationException {
        // 当前请求路径
//        String requestUrl = ((FilterInvocation) object).getRequestUrl();
//        int index = requestUrl.lastIndexOf("?");
//        if (index != -1) {
//            requestUrl = requestUrl.substring(0, index);
//        }
//        Object temp = SecurityContextHolder.getContext().getAuthentication().getDetails();
//        AntPathMatcher antPathMatcher = new AntPathMatcher();
//        //确定角色id、根据角色id查询该角色对应的 api 接口然后判断当前访问是否具有权限
//        String roleId = temp instanceof Authentication ? ((UserRole)temp).getRole() : "0";
//        List<Object> api = new ArrayList<>();
//        String redisKey = roleId + "RoleApi";
//        if (redisUtil.hasKey(redisKey)){
//            api = redisUtil.lGet(redisKey,0,-1);
//        }else {
//            List<Permission> list = rolePermissionMapper.getRoleApi(roleId);
//            for (Permission item : list){
//                if (item.getInterfacePath() != null)  api.add(item.getInterfacePath()) ;
//            }
//            redisUtil.lSet(redisKey,api);
//        }
//
//        if(!CollectionUtils.isEmpty(api)){
//            for (Object item : api){
//                if (antPathMatcher.match(item.toString(),requestUrl)){
//                    return;
//                }
//            }
//        }
//        throw new AccessDeniedException("403 not authentication");
    }

    @Override
    public boolean supports(ConfigAttribute attribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
}

