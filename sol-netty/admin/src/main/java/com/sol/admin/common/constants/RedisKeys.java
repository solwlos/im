package com.sol.admin.common.constants;


/**
 * @author sol
 * @since 2024/8/19
 */
public class RedisKeys {

    // 系统用户token
    public static final String SYS_USER_PREFIX = "sysUser::";
    // 前缀 普通用户token
    public static final String USER_PREFIX = "user::";
    // 菜单
    public static final String MENU_PREFIX = "menu::";
    // 角色
    public static final String ROLE_PREFIX = "role::";

    // token
    public static final String USER_TOKEN = USER_PREFIX +"token";
    public static final String SYS_USER_TOKEN = SYS_USER_PREFIX +"token";

    // 系统用户信息
    public static final String SYS_USER_INFO = SYS_USER_PREFIX +"info";

    // 系统用户 api
    public static final String SYS_ROLE_API = ROLE_PREFIX +"api";
}
