package com.sol.admin.modules.system.service;

import com.sol.admin.modules.system.entity.SysRolePermission;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 角色权限表 服务类
 * </p>
 *
 * @author sol
 * @since 2024-08-21
 */
public interface SysRolePermissionService {

    List<SysRolePermission> getRolePermission(Long roleId);
}
