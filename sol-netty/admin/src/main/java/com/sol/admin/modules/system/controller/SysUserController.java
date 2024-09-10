package com.sol.admin.modules.system.controller;

import com.sol.admin.modules.system.dto.UserDTO;
import com.sol.admin.modules.system.entity.SysUser;
import com.sol.admin.modules.system.service.SysUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.ServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    SysUserService sysUserService;


    @PostMapping("/login")
    @Operation(summary ="登录")
    public  ResponseEntity<?> sysUserLogin(ServletRequest request,@RequestBody UserDTO userDTO){
        log.info("用户名:{},密码:{}",userDTO.getUsername(),userDTO.getPassword());
        return sysUserService.sysUserLogin(request, userDTO.getUsername(),userDTO.getPassword());
//        return Result.success(claims,"登陆成功");
    }



    @PostMapping("/add")
    @Operation(summary ="添加用户")
    public ResponseEntity<Object> addUser(@RequestBody SysUser sysUser){
        return ResponseEntity.status(HttpStatus.OK).body(sysUserService.addUser(sysUser));
    }

}
