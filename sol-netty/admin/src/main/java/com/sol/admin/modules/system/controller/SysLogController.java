package com.sol.admin.modules.system.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 系统日志 前端控制器
 * </p>
 *
 * @author sol
 * @since 2024-08-21
 */
@RestController
@RequestMapping("/sysLog")
public class SysLogController {


    @PostMapping("/searchQuery")
    @Operation(summary ="添加用户")
    public ResponseEntity<?> searchQuery(){

        return null;
    }
}
