package com.sol.admin.modules.system.service.impl;

import com.sol.admin.modules.system.entity.SysMenu;
import com.sol.admin.modules.system.mapper.SysMenuMapper;
import com.sol.admin.modules.system.service.SysMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
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
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {

    @Resource
    SysMenuMapper sysMenuMapper;

    @Override
    public Boolean addMenu(SysMenu sysMenu) {

        return sysMenuMapper.insert(sysMenu) > 0;
    }
}
