package com.sol.admin.modules.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sol.admin.common.constants.StatusType;
import com.sol.admin.modules.system.entity.SysMenu;
import com.sol.admin.modules.system.mapper.SysMenuMapper;
import com.sol.admin.modules.system.service.SysMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import java.util.List;

import org.springframework.stereotype.Service;

/**
 * <p>
 * 菜单表 服务实现类
 * </p>
 *
 * @author sol
 * @since 2024-02-20
 */
@Service
public class SysMenuServiceImpl  implements SysMenuService {

    @Resource
    SysMenuMapper mapper;

    @Override
    public Boolean addMenu(SysMenu sysMenu) {
        return mapper.insert(sysMenu) == 1;
    }

    @Override
    public List<SysMenu> getRootMenu() {
        return mapper.getRootMenu();
    }

    @Override
    public Boolean deletedMenu(String id) {
        return mapper.deleteById(id) == 1;
    }

    @Override
    public Boolean updateMenu(SysMenu sysMenu) {
        return mapper.updateById(sysMenu) == 1;
    }

    @Override
    public List<SysMenu> getSonMenu(String pid) {
        return mapper.getSonMenu(pid);
    }
}
