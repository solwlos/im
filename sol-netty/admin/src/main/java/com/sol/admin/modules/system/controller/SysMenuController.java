package com.sol.admin.modules.system.controller;

import com.sol.admin.modules.system.dto.MenuDTO;
import com.sol.admin.modules.system.entity.SysMenu;
import com.sol.admin.modules.system.service.SysMenuService;
import io.swagger.v3.oas.annotations.Operation;

import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "/sysMenu", description = "菜单")
public class SysMenuController {

    @Resource
    SysMenuService sysMenuService;


    @PostMapping("/addMenu")
    @Operation(summary ="添加菜单")
    public ResponseEntity<Boolean> addMenu(@RequestBody SysMenu sysMenu){
        return new ResponseEntity<>(sysMenuService.addMenu(sysMenu), HttpStatus.OK);
    }

    @PutMapping("/updateMenu")
    @Operation(summary ="修改菜单")
    public ResponseEntity<Boolean> updateMenu(@RequestBody SysMenu sysMenu){
        return new ResponseEntity<>(sysMenuService.updateMenu(sysMenu), HttpStatus.OK);
    }

    @DeleteMapping("/deletedMenu")
    @Operation(summary ="删除菜单")
    public ResponseEntity<Boolean> deletedMenu(String id){
        return new ResponseEntity<>(sysMenuService.deletedMenu(id), HttpStatus.OK);
    }

    @GetMapping("/getRootMenu")
    @Operation(summary ="获得根节点")
    public ResponseEntity<List<SysMenu>> getRootMenu(){
        return new ResponseEntity<>(sysMenuService.getRootMenu(), HttpStatus.OK);
    }

    @GetMapping("/getSonMenu")
    @Operation(summary ="获得子节点")
    public ResponseEntity<List<SysMenu>> getSonMenu(String pid){
        return new ResponseEntity<>(sysMenuService.getSonMenu(pid), HttpStatus.OK);
    }

    @GetMapping("/getMenuTree")
    @Operation(summary ="获得菜单树")
    public ResponseEntity<List<MenuDTO>> getMenuTree(){
        return new ResponseEntity<>(sysMenuService.getMenuTree(), HttpStatus.OK);
    }

}
