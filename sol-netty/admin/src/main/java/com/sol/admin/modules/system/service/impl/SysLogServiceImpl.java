package com.sol.admin.modules.system.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sol.admin.modules.base.EntitySearchQuery;
import com.sol.admin.modules.system.entity.SysLog;
import com.sol.admin.modules.system.mapper.SysLogMapper;
import com.sol.admin.modules.system.service.SysLogService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


/**
 * <p>
 * 系统日志 服务实现类
 * </p>
 *
 * @author sol
 * @since 2024-08-21
 */
@Slf4j
@Service
public class SysLogServiceImpl implements SysLogService {

    @Resource
    private SysLogMapper mapper;

    @Override
    public Page<SysLog> searchQuery(EntitySearchQuery<SysLog> query){
        return mapper.searchQuery(query);
    }

    @Override
    public Boolean addSysLog(SysLog sysLog) {
        return mapper.insert(sysLog) == 1;
    }
}
