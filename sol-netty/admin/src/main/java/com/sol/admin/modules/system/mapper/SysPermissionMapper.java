package com.sol.admin.modules.system.mapper;

import com.sol.admin.modules.system.entity.SysPermission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.swagger.v3.oas.models.tags.Tag;

import java.util.List;

/**
 * <p>
 * 权限表 Mapper 接口
 * </p>
 *
 * @author sol
 * @since 2024-08-21
 */
public interface SysPermissionMapper extends BaseMapper<SysPermission> {

    List<Tag> getTags();

    void delAll(List<String> list);

//    void insertBatch(List<SysPermission> newPermissions);
}
