package com.sol.admin.modules.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sol.admin.modules.system.entity.SysLog;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 系统日志 服务类
 * </p>
 *
 * @author sol
 * @since 2024-08-21
 */
public interface SysLogService {

    Page<SysLog> searchQuery();
    Boolean addSysLog(SysLog sysLog);
}
