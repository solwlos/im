package com.sol.admin.modules.system.controller;

import com.sol.admin.modules.base.EntitySearchQuery;
import com.sol.admin.modules.system.dto.UserDTO;
import com.sol.admin.modules.system.entity.SysUser;
import com.sol.admin.modules.system.service.SysUserService;
import io.swagger.v3.core.util.Json;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.ServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author sol
 * @since 2024-02-20
 */
@Slf4j
@RestController
@RequestMapping("/sysUser")
@Tag(name = "/sysUser", description = "用户")
public class SysUserController {


    @Autowired
    SysUserService service;


    @PostMapping("/login")
    @Operation(summary ="登录")
    public  ResponseEntity<?> sysUserLogin(ServletRequest request,@RequestBody UserDTO userDTO){
        log.info("用户名:{},密码:{}",userDTO.getUsername(),userDTO.getPassword());
        return service.sysUserLogin(request, userDTO.getUsername(),userDTO.getPassword());
//        return Result.success(claims,"登陆成功");
    }



    @PostMapping("/add")
    @Operation(summary ="添加用户")
    public ResponseEntity<Object> addUser(@RequestBody SysUser sysUser){
        return ResponseEntity.status(HttpStatus.OK).body(service.addUser(sysUser));
    }

    @PutMapping("/update")
    @Operation(summary ="修改用户")
    public ResponseEntity<Object> updateUser(@RequestBody SysUser sysUser){
        return ResponseEntity.status(HttpStatus.OK).body(service.updateUser(sysUser));
    }

//    @PostMapping("/searchQuery")
//    @Operation(summary ="用户搜索分页")
//    public ResponseEntity<Page<SysUser>> searchQuery(@RequestBody EntitySearchQuery<SysUser> query){
//        log.info("searchQuery:{}", Json.pretty(query));
//        return new ResponseEntity<>(service.searchQuery(query), HttpStatus.OK);
//    }
    @PostMapping("/searchQuery")
    @Operation(summary ="用户搜索分页")
    @ResponseBody
    public ResponseEntity<String> searchQuery(@RequestBody EntitySearchQuery<SysUser> query){
        String res =  "";
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

}
