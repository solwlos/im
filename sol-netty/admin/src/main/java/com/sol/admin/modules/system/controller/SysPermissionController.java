package com.sol.admin.modules.system.controller;

import com.sol.admin.modules.system.entity.SysPermission;
import com.sol.admin.modules.system.entity.SysUser;
import com.sol.admin.modules.system.service.SysPermissionService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
public class SysPermissionController {

    @Autowired
    SysPermissionService service;

    @PostMapping("/list")
    public Object list(){
        return null;
    }


    @PostMapping("/add")
    @Operation(summary ="添加权限")
    public ResponseEntity<Boolean> add(@RequestBody SysPermission permission){
        return ResponseEntity.status(HttpStatus.OK).body(service.add(permission));
    }

}
