package com.sol.admin.modules.system.controller;

import com.sol.admin.modules.base.EntitySearchQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sol.admin.modules.system.entity.SysRole;
import com.sol.admin.modules.system.service.SysRoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


/**
 * <p>
 * 角色表 前端控制器
 * </p>
 *
 * @author sol
 * @since 2024-02-20
 */
@RestController
@RequestMapping("/sysRole")
@Tag(name = "/sysRole", description = "角色")
public class SysRoleController {

    @Autowired
    private SysRoleService service;

    @PostMapping("/addRole")
    @Operation(summary ="添加角色")
    public ResponseEntity<Boolean> addRole(@RequestBody SysRole sysRole){
        return new ResponseEntity<>(service.addRole(sysRole), HttpStatus.OK);
    }

    @PutMapping("/updateRole")
    @Operation(summary ="修改角色")
    public ResponseEntity<Boolean> updateRole(@RequestBody SysRole sysRole){
        return new ResponseEntity<>(service.updateRole(sysRole), HttpStatus.OK);
    }

    @DeleteMapping("/deletedRole")
    @Operation(summary ="删除角色")
    public ResponseEntity<Boolean> deletedRole(String id){
        return new ResponseEntity<>(service.deletedRole(id), HttpStatus.OK);
    }

    @PostMapping("/searchQuery")
    @Operation(summary ="角色搜索分页")
    public ResponseEntity<Page<SysRole>> searchQuery(@RequestBody EntitySearchQuery<SysRole> query){
        return new ResponseEntity<>(service.searchQuery(query), HttpStatus.OK);
    }

}
