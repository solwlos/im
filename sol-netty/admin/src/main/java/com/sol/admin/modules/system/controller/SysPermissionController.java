package com.sol.admin.modules.system.controller;

import com.sol.admin.modules.system.dto.MenuDTO;
import com.sol.admin.modules.system.entity.SysMenu;
import com.sol.admin.modules.system.entity.SysPermission;
import com.sol.admin.modules.system.entity.SysUser;
import com.sol.admin.modules.system.service.SysPermissionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 权限表 前端控制器
 * </p>
 *
 * @author sol
 * @since 2024-08-21
 */
@RestController
@RequestMapping("/sysPermission")
@Tag(name = "/sysPermission", description = "权限")
public class SysPermissionController {

    @Autowired
    SysPermissionService service;

    @PostMapping("/list")
    public ResponseEntity<List<SysPermission>> list(){
        return null;
    }


    @PostMapping("/add")
    @Operation(summary ="添加权限")
    public ResponseEntity<Boolean> add(@RequestBody SysPermission permission){
        return ResponseEntity.status(HttpStatus.OK).body(service.add(permission));
    }

    @GetMapping("/getRootMenu")
    @Operation(summary ="获得根节点")
    public ResponseEntity<List<SysPermission>> getRoot(){
        return new ResponseEntity<>(service.getRoot(), HttpStatus.OK);
    }

    @GetMapping("/getSon")
    @Operation(summary ="获得子节点")
    public ResponseEntity<List<SysPermission>> getSon(String pid){
        return new ResponseEntity<>(service.getSonMenu(pid), HttpStatus.OK);
    }

    @GetMapping("/getTree")
    @Operation(summary ="获得菜单树")
    public ResponseEntity<List<SysPermission>> getTree(){
        return new ResponseEntity<>(service.getMenuTree(), HttpStatus.OK);
    }

}
