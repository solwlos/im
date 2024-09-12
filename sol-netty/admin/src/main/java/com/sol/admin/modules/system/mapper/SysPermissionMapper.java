package com.sol.admin.modules.system.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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
        queryWrapper.lambda().eq(SysPermission::getIsRoot,1);
        return selectList(queryWrapper);
    }

    default void delAll(List<String> list){
        Map<String, Object> map = Map.of("name", list);
        deleteByMap(map);
    }
}
