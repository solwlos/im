package com.sol.admin.modules.system.mapper;

import com.sol.admin.modules.system.entity.SysUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sol.admin.modules.system.entity.UserRole;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author sol
 * @since 2024-02-20
 */
public interface SysUserMapper extends BaseMapper<SysUser> {
    /**
     *  查询 email 和 username 其中有一个相同就返回该用户
     * @param loginName
     * @return
     */
    SysUser getLoginName(@Param("loginName")String loginName);

    UserRole getUserRole(@Param("username")String username);
    UserRole getUserMail(@Param("email")String email);
}
