package com.sol.admin.common.constants;


/**
 * @author sol
 * @since 2024/8/19
 */
public class RedisKeys {

    // 系统用户token
    public static final String SYS_USER_PREFIX = "sysUser::";
    // 前缀
    public static final String USER_PREFIX = "user::";
    // token
    public static final String USER_TOKEN = USER_PREFIX +"token";
    public static final String SYS_USER_TOKEN = SYS_USER_PREFIX +"token";

    public static final String SYS_USER_INFO = SYS_USER_PREFIX +"info";
}
