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
public interface SysPermissionService extends IService<SysPermission> {

    Boolean add(SysPermission permission);

    List<Tag> getTags();

    void delAll(List<String> list);
}
