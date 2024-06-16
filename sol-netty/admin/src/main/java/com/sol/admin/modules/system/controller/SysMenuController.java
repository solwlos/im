package com.sol.admin.modules.system.controller;

import com.sol.admin.modules.system.entity.SysMenu;
import com.sol.admin.modules.system.service.SysMenuService;
import io.swagger.v3.oas.annotations.Operation;

import jakarta.annotation.Resource;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 菜单表 前端控制器
 * </p>
 *
 * @author sol
 * @since 2024-02-20
 */
@RestController
@RequestMapping("/sysMenu")
public class SysMenuController {

    @Resource
    SysMenuService sysMenuService;


    @PostMapping("/addMenu")
    @Operation(summary ="添加菜单")
    public ResponseEntity<Boolean> addMenu(@RequestBody SysMenu sysMenu){
        return new ResponseEntity<>(sysMenuService.save(sysMenu), HttpStatus.OK);
    }

    @PutMapping("/updateMenu")
    @Operation(summary ="修改菜单")
    public ResponseEntity<Boolean> updateMenu(@RequestBody SysMenu sysMenu){
        return new ResponseEntity<>(sysMenuService.updateById(sysMenu), HttpStatus.OK);
    }

    @DeleteMapping("/deletedMenu")
    @Operation(summary ="删除菜单")
    public ResponseEntity<Boolean> deletedMenu(String id){
        return new ResponseEntity<>(sysMenuService.removeById(id), HttpStatus.OK);
    }

    @GetMapping("/getRootMenu")
    @Operation(summary ="获得根节点")
    public ResponseEntity<List<SysMenu>> getRootMenu(){
        return new ResponseEntity<>(sysMenuService.getRootMenu(), HttpStatus.OK);
    }

}
