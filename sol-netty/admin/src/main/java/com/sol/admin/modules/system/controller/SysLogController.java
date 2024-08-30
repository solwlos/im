package com.sol.admin.modules.system.controller;

import com.alibaba.fastjson.JSON;
import com.sol.admin.modules.base.EntitySearchQuery;
import com.sol.admin.modules.system.entity.SysLog;
import com.sol.admin.modules.system.service.SysLogService;
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
 * 系统日志 前端控制器
 * </p>
 *
 * @author sol
 * @since 2024-08-21
 */
@RestController
@RequestMapping("/sysLog")
public class SysLogController {

    @Autowired
    private SysLogService sysLogService;

    @PostMapping("/searchQuery")
    @Operation(summary ="分页搜索")
    public ResponseEntity<?> searchQuery(@RequestBody EntitySearchQuery<SysLog> query){
        return ResponseEntity.status(HttpStatus.OK).body(sysLogService.searchQuery(query));
    }
}
