package com.sol.admin.modules.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sol.admin.modules.base.EntitySearchQuery;
import com.sol.admin.modules.system.entity.SysLog;

/**
 * <p>
 * 系统日志 服务类
 * </p>
 *
 * @author sol
 * @since 2024-08-21
 */
public interface SysLogService {

    Page<SysLog> searchQuery(EntitySearchQuery<SysLog> query);
    Boolean addSysLog(SysLog sysLog);
}
