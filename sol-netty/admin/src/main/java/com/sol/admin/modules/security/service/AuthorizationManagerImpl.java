package com.sol.admin.modules.security.service;

import com.sol.admin.common.constants.RedisKeys;
import com.sol.admin.common.util.RedisUtil;
import com.sol.admin.modules.security.util.JwtUtil;
import com.sol.admin.modules.system.dto.UserInfo;
import com.sol.admin.modules.system.entity.SysPermission;
import com.sol.admin.modules.system.service.SysPermissionService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.util.AntPathMatcher;;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.function.Supplier;

/**
 * date: 2024/2/23
 *
 * @author sol
 */
@Slf4j
public class AuthorizationManagerImpl implements AuthorizationManager<RequestAuthorizationContext> { //RequestAuthorizationContext


    @Autowired
    SysPermissionService sysPermissionService;

    @Autowired
    HttpServletResponse response;
    @Resource
    RedisUtil redisUtil;

//    @Autowired
//    private UserDao userDao;
    @Autowired
    private JwtUtil jwtUtil;

    /**
     * 判断用户是否拥有访问该资源的权限
     * 当有一些资源不需要认证，例如：登录、注册、验证码等，可以配置在白名单中，直接放行，不需要认证，但需要记录访问日志。
     * 这里设置一个 角色id为0，角色名字为 anonymous，控制不需要授权的 api
     * @param authentication the {@link Supplier} of the {@link Authentication} to check
     * @param context the {@link // T} object to check
     * @return
     */
    @Override
    @SuppressWarnings("unchecked")
    public AuthorizationDecision check(Supplier<Authentication> authentication, RequestAuthorizationContext context){

        // 当前请求路径, 去掉参数
        String requestUrl = context.getRequest().getRequestURI();
        // 获取当前用户信息
        Authentication auth = authentication.get();
//        log.info("当前用户信息:{}", Json.pretty(auth));
        AntPathMatcher antPathMatcher = new AntPathMatcher();
        // 获取对应用户对应角色  角色id：0 、角色名称 anonymous，代表未登录 （验证不需要授权的 api）
        Long roleId = auth.getDetails() instanceof UserInfo ? ((UserInfo)auth.getDetails()).getRoleId() : 0L;
        String roleName = auth.getDetails() instanceof UserInfo ? ((UserInfo)auth.getDetails()).getRoleName() : "anonymous";

        // 从 redis 中获取角色具有访问权限的 api
        List<SysPermission> list = null;
        if (!redisUtil.hHasKey(RedisKeys.SYS_ROLE_API,roleName)){  // 没有缓存，从数据库查询
            list = sysPermissionService.getPermissionByRoleId(roleId);
            redisUtil.hset(RedisKeys.SYS_ROLE_API,roleName,list);
        }
        // 有缓存，从redis获取
        list = (List<SysPermission>) redisUtil.hget(RedisKeys.SYS_ROLE_API,roleName);

//        log.info("当前角色：{}，角色权限：{}",roleName,Json.pretty(list));
        // 判断该角色是否具有 该api 访问权限
        for (SysPermission item : list){
            if (antPathMatcher.match(item.getName(),requestUrl)){
                return new AuthorizationDecision(true);
            }
        }
        // 不允许访问
        return new AuthorizationDecision(false);

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
