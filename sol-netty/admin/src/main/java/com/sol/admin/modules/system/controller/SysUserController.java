package com.sol.admin.modules.system.controller;

import com.sol.admin.modules.security.util.JwtUtil;
import com.sol.admin.modules.system.entity.SysUser;
import com.sol.admin.modules.system.service.SysMenuService;
import com.sol.admin.modules.system.service.SysUserService;
import com.sol.admin.util.RedisUtil;
import com.sol.admin.util.Result;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;
import org.springframework.web.servlet.resource.ResourceUrlEncodingFilter;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author sol
 * @since 2024-02-20
 */
@RestController
@RequestMapping("/sysUser")
public class SysUserController {






    @Autowired
    SysUserService sysUserService;


    @GetMapping("/sysUserLogin")
    @Operation(summary ="登录")
    public  ResponseEntity<?> sysUserLogin(ServletRequest request , String username, String password){
        return new ResponseEntity<>(sysUserService.sysUserLogin(request,username,password), HttpStatus.OK);
//        return Result.success(claims,"登陆成功");
    }



    @PostMapping("/addUser")
    @Operation(summary ="添加用户")
    public ResponseEntity<Object> addUser(@RequestBody SysUser sysUser){
//        return new ResponseEntity<>(sysUserService.save(sysUser), HttpStatus.OK);
        return ResponseEntity.status(HttpStatus.OK).body(sysUserService.save(sysUser));
    }

}
