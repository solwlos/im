package com.sol.admin.modules.system.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sol.admin.modules.base.EntitySearchQuery;
import com.sol.admin.modules.system.dto.UserInfo;
import com.sol.admin.modules.system.entity.SysUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sol.admin.modules.system.dto.UserRole;
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
     */
    SysUser getLoginName(@Param("username")String username);

    UserRole getUserRole(@Param("username")String username);

    UserInfo getUserInfo(@Param("username")String username);

    default Page<SysUser> searchQuery(EntitySearchQuery<SysUser> query){
//        Page<SysUser> page = new Page<>(query.getPage().getCurrent(),query.getPage().getSize());
        Page<SysUser> page = new Page<>(1,10);
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
//        queryWrapper.lambda()
//                .like(SysRole::getName,query.getEntity().getName());
        page = selectPage(page,queryWrapper);
        page.setTotal(selectCount(queryWrapper));
        return page;
    }
}
