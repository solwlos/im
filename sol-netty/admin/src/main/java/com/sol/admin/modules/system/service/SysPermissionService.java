package com.sol.admin.modules.system.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sol.admin.modules.system.entity.SysPermission;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sol.admin.modules.system.mapper.SysPermissionMapper;
import io.swagger.v3.oas.models.tags.Tag;

import java.util.List;

/**
 * <p>
 * 权限表 服务类
 * </p>
 *
 * @author sol
 * @since 2024-08-21
 */
public interface SysPermissionService {

    Boolean add(SysPermission permission);

    List<Tag> getTags();

    List<SysPermission> getIsNotRoot();
    void insertBatch(List<SysPermission> list);

    void removeByIds(List<Long> ids);

    void delAll(List<String> delPermissions);

    List<SysPermission> getPermissionByRoleId(Long roleId);

    List<SysPermission> getRoot();

    List<SysPermission> getSonMenu(String pid);

    List<SysPermission> getMenuTree();
}
