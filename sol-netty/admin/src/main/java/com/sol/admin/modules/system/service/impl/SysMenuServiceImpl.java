package com.sol.admin.modules.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sol.admin.modules.system.dto.MenuDTO;
import com.sol.admin.modules.system.entity.SysMenu;
import com.sol.admin.modules.system.mapper.SysMenuMapper;
import com.sol.admin.modules.system.service.SysMenuService;
import jakarta.annotation.Resource;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

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

    @Override
    public List<MenuDTO> getMenuTree() {
//        QueryWrapper<SysMenu> queryWrapper =  new QueryWrapper<>();
//        queryWrapper.lambda().eq(SysMenu::getIsDeleted,0);
        // 获取所有菜单
        List<SysMenu> allMenus = mapper.selectList(null);
//        List<SysMenu> allMenus = mapper.getList();
        // 将所有菜单放入Map中，以id作为键
        Map<Long, MenuDTO> menuMap = allMenus.stream()
                .map(menu -> MenuDTO.builder()    // 将SysMenu转换为MenuDTO
                                .id(menu.getId())
                                .pid(menu.getPid())
                                .name(menu.getName())
                                .path(menu.getPath())
                                .children(new ArrayList<>())
                                .build()
                )
                .collect(Collectors.toMap(MenuDTO::getId, Function.identity()));
        // 构建菜单树
        List<MenuDTO> rootMenus = new ArrayList<>();
        menuMap.forEach((id, menuDTO) -> {
            if (menuDTO.getPid() == 0) {
                rootMenus.add(menuDTO);
            } else {
                MenuDTO parentMenu = menuMap.get(menuDTO.getPid());
                if (parentMenu != null) {
                    if (parentMenu.getChildren() == null) {
                        parentMenu.setChildren(new ArrayList<>());
                    }
                    parentMenu.getChildren().add(menuDTO);
                }
            }
        });

        return rootMenus;
    }
}
