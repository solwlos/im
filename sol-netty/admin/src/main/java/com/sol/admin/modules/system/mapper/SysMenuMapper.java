package com.sol.admin.modules.system.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sol.admin.common.constants.StatusType;
import com.sol.admin.modules.system.entity.SysMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import java.util.List;

/**
 * <p>
 * 菜单表 Mapper 接口
 * </p>
 *
 * @author sol
 * @since 2024-02-20
 */
public interface SysMenuMapper extends BaseMapper<SysMenu> {
    default List<SysMenu> getSonMenu(String pid){
        QueryWrapper<SysMenu> queryWrapper = new QueryWrapper<>();
        queryWrapper
                .lambda()
                .eq(SysMenu::getPid, pid)
//                .eq(SysMenu::getStatus, StatusType.NORMAL)
                .eq(SysMenu::getIsDeleted, 0);
        return selectList(queryWrapper);
    }

    default List<SysMenu> getRootMenu(){
        QueryWrapper<SysMenu> queryWrapper = new QueryWrapper<>();
        queryWrapper
                .lambda()
                .eq(SysMenu::getPid, 0)
//                .eq(SysMenu::getStatus, StatusType.NORMAL)
                .eq(SysMenu::getIsDeleted, 0);
        return selectList(queryWrapper);
    }
}
