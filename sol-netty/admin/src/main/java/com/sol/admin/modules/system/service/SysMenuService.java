package com.sol.admin.modules.system.service;

import com.sol.admin.modules.system.entity.SysMenu;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * <p>
 * 菜单表 服务类
 * </p>
 *
 * @author sol
 * @since 2024-02-20
 */
public interface SysMenuService {

    Boolean addMenu(SysMenu sysMenu);

    List<SysMenu> getRootMenu();

    Boolean deletedMenu(String id);

    Boolean updateMenu(SysMenu sysMenu);

    List<SysMenu> getSonMenu(String pid);
}
