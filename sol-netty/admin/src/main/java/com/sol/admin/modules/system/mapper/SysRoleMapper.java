package com.sol.admin.modules.system.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sol.admin.modules.base.EntitySearchQuery;
import com.sol.admin.modules.system.entity.SysLog;
import com.sol.admin.modules.system.entity.SysRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 角色表 Mapper 接口
 * </p>
 *
 * @author sol
 * @since 2024-02-20
 */
public interface SysRoleMapper extends BaseMapper<SysRole> {

    default Page<SysRole> searchQuery(EntitySearchQuery<SysRole> query){
        Page<SysRole> page = new Page<>(query.getPage().getCurrent(),query.getPage().getSize());
        QueryWrapper<SysRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .like(SysRole::getName,query.getEntity().getName());
        return selectPage(page,queryWrapper);
    }
}
