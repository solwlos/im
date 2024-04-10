package com.sol.admin.modules.system.service;

import com.sol.admin.modules.system.entity.SysMenu;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 菜单表 服务类
 * </p>
 *
 * @author sol
 * @since 2024-02-20
 */
public interface SysMenuService extends IService<SysMenu> {

    Boolean addMenu(SysMenu sysMenu);
}
