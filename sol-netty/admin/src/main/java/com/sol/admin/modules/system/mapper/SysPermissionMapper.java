package com.sol.admin.modules.system.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.sol.admin.modules.system.entity.SysPermission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.swagger.v3.oas.models.tags.Tag;

import java.util.List;
import java.util.Map;

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

    default List<SysPermission> getIsNotRoot(){
        QueryWrapper<SysPermission> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(SysPermission::getType,1);
        return selectList(queryWrapper);
    }

    default void delAll(List<String> list){
        UpdateWrapper<SysPermission> queryWrapper = new UpdateWrapper <>();
        queryWrapper.in("name", list).eq("is_deleted", 0);
        // 设置要更新的字段和值
        update(null, queryWrapper.set("is_deleted", 1));
    }

    List<SysPermission> getPermissionByRoleId(Long roleId);
}
