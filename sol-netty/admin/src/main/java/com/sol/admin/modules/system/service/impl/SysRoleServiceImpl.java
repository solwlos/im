package com.sol.admin.modules.system.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sol.admin.modules.base.EntitySearchQuery;
import com.sol.admin.modules.system.entity.SysMenu;
import com.sol.admin.modules.system.entity.SysRole;
import com.sol.admin.modules.system.mapper.SysRoleMapper;
import com.sol.admin.modules.system.service.SysRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @author sol
 * @since 2024-02-20
 */
@Service
public class SysRoleServiceImpl implements SysRoleService {

    @Resource
    SysRoleMapper mapper;
    @Override
    public Boolean deletedRole(String id) {
        return mapper.deleteById(id) == 1;
    }

    @Override
    public Boolean updateRole(SysRole sysRole) {
        return mapper.updateById(sysRole) == 1;
    }

    @Override
    public Boolean addRole(SysRole sysRole) {
        return mapper.insert(sysRole) == 1;
    }

    @Override
    public Page<SysRole> searchQuery(EntitySearchQuery<SysRole> query) {
        return mapper.searchQuery(query);
    }
}
