package com.sol.admin.modules.security.filter;

import com.sol.admin.modules.system.entity.UserRole;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * @author sol
 * @date 2024/1/10 14:38
 * @Version 1.0
 */

@Slf4j
@Service
public class MenuFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {


    /**
     * @param object the object being secured
     * @return
     * @throws IllegalArgumentException
     */
    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        Set<ConfigAttribute> set = new HashSet<>();
        // 访问的ip
        //String ipUrl = ((FilterInvocation) object).getHttpRequest().getRemoteAddr();
        Object temp = SecurityContextHolder.getContext().getAuthentication();
        if (temp instanceof UserRole && ((UserRole) temp).getRole() != null){
            return SecurityConfig.createList(((UserRole) temp).getRole());
        }

        if (ObjectUtils.isEmpty(set)) {
            return SecurityConfig.createList("0");
        }
        return set;
    }
    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }
    @Override
    public boolean supports(Class<?> clazz) {
        return FilterInvocation.class.isAssignableFrom(clazz);
    }
}