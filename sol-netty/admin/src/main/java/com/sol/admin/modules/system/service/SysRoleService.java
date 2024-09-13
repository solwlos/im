package com.sol.admin.modules.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sol.admin.modules.base.EntitySearchQuery;
import com.sol.admin.modules.system.entity.SysMenu;
import com.sol.admin.modules.system.entity.SysRole;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 角色表 服务类
 * </p>
 *
 * @author sol
 * @since 2024-02-20
 */
public interface SysRoleService {

    Boolean deletedRole(String id);

    Boolean updateRole(SysRole sysRole);

    Boolean addRole(SysRole sysRole);

    Page<SysRole> searchQuery(EntitySearchQuery<SysRole> query);
}
