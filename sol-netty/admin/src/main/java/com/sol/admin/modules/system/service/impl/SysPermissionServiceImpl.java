package com.sol.admin.modules.system.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.sol.admin.modules.system.entity.SysPermission;
import com.sol.admin.modules.system.mapper.SysPermissionMapper;
import com.sol.admin.modules.system.service.SysPermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.swagger.v3.oas.models.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 权限表 服务实现类
 * </p>
 *
 * @author sol
 * @since 2024-08-21
 */
@Service
public class SysPermissionServiceImpl implements SysPermissionService{


    @Resource
    private SysPermissionMapper mapper;

    @Override
    public Boolean add(SysPermission permission) {
        return mapper.insert(permission) == 1;
    }

    @Override
    public List<Tag> getTags() {
        return mapper.getTags();
    }

    @Override
    public void delAll(List<String> list) {
        mapper.delAll(list);
    }

    @Override
    public List<SysPermission> getPermissionByRoleId(Long roleId) {
        return mapper.getPermissionByRoleId(roleId);
    }

    @Override
    public List<SysPermission> getRoot() {
        return null;
    }

    @Override
    public List<SysPermission> getSonMenu(String pid) {
        return null;
    }

    @Override
    public List<SysPermission> getMenuTree() {
        return null;
    }

    @Override
    public List<SysPermission> getIsNotRoot() {
        return mapper.getIsNotRoot();
    }

    @Override
    public void insertBatch(List<SysPermission> list) {
        mapper.insert(list,1000);
    }

    @Override
    public void removeByIds(List<Long> ids) {
        mapper.deleteByIds(ids);
    }
}
