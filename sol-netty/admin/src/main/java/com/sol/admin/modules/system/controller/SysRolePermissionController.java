package com.sol.admin.modules.system.controller;

import io.swagger.annotations.ApiImplicitParams;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 角色权限表 前端控制器
 * </p>
 *
 * @author sol
 * @since 2024-08-21
 */
@RestController
@RequestMapping("/sysRolePermission")
@Tag(name = "/sysRolePermission", description = "角色权限")
public class SysRolePermissionController {


    @PostMapping("/edit")
    @Operation(summary ="修改角色对应的权限")
    public Boolean edit(@RequestParam("rid") Long rid, @RequestParam("pIds")List<Long> pIds){



        return null;
    }


}
