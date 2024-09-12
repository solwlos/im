package com.sol.admin.modules.security.service;

import com.sol.admin.common.util.RedisUtil;
import com.sol.admin.modules.security.util.JwtUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.security.web.util.UrlUtils;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.function.Supplier;

/**
 * date: 2024/2/23
 *
 * @author sol
 */
@Slf4j
public class AuthorizationManagerImpl implements AuthorizationManager<RequestAuthorizationContext> { //RequestAuthorizationContext

//    @Resource
//    RolePermissionDao rolePermissionDao;
    @Autowired
    HttpServletResponse response;
    @Resource
    RedisUtil redisUtil;

//    @Autowired
//    private UserDao userDao;
    @Autowired
    private JwtUtil jwtUtil;
    @Override // FilterInvocation
    public AuthorizationDecision check(Supplier<Authentication> authentication, RequestAuthorizationContext context){

        System.out.println("======= 权限判断  =========");
        // 当前请求路径, 去掉参数
//        String requestUrl = ((FilterInvocation) object).getRequestUrl();
        String requestUrl = context.getRequest().getRequestURI();
        int index = requestUrl.lastIndexOf("?");

        if (index != -1) requestUrl = requestUrl.substring(0, index);

        log.info("requestUrl:{}", requestUrl);

        // sse 连接
//        if (requestUrl.equals("/sse/connect")){
//            try {
//                // 判断 token 是否有效
//                checkAllowIfAllAbstainDecisions(((FilterInvocation) object).getRequestUrl());
//                return;
//            }catch (Exception e){
//                throw new AccessDeniedException(e + "");
//            }
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
////            List<Permission> list = rolePermissionDao.getRoleApi(roleId);
////            for (Permission item : list){
////                String path = item.getInterface_path();
////                if (path != null && !api.contains(path))  api.add(path) ;
////            }
//            redisUtil.lSet(redisKey,api);
//        }
//        if(!CollectionUtils.isEmpty(api)){
//            for (Object item : api){
//                if (antPathMatcher.match(item.toString(),requestUrl)){
////                    return;
//                }
//            }
//        }
//        checkAllowIfAllAbstainDecisions();
//        throw new AccessDeniedException(roleId + " > "+ requestUrl + " 403 not authentication");

        // 允许访问
        return new AuthorizationDecision(true);
//        return null;
    }

    /**
     * 检查 url 中的 token 是否有效
     * @param requestUrl 完整路径 【携带参数的】
     */
    public void checkAllowIfAllAbstainDecisions(String requestUrl){

        String[] paramsArray = requestUrl.split("&");
        for (String param : paramsArray) {
            String[] keyValue = param.split("=");
            String key = URLDecoder.decode(keyValue[0], StandardCharsets.UTF_8);
            String value = URLDecoder.decode(keyValue[1], StandardCharsets.UTF_8);
            if (key.equals("token")){
                // 验证是否过期
                jwtUtil.isTokenExpired(value);
                //比较 登录用户名
                String username = jwtUtil.getUserNameFromToken(value);//获取token中的用户名
                if (username != null) {
                    // 查询数据库里面是否有该账号、在sql层面做了缓存60秒刷新
//                    UserRole userRole = userDao.getUserRole(username);
//                    if (userRole == null) {
//                        userRole = userDao.getUserMail(username);
//                    }
//                    // 查询数据库里面是否有该邮箱号
//                    if (userRole == null) {
//                        throw new AccessDeniedException("token 无效，没有此用户！！！");
//                    }
                }

            }
        }

    }
}
