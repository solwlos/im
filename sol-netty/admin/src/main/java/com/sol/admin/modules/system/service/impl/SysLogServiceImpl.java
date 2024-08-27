package com.sol.admin.modules.system.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sol.admin.modules.system.entity.SysLog;
import com.sol.admin.modules.system.mapper.SysLogMapper;
import com.sol.admin.modules.system.service.SysLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

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
    private SysLogMapper sysLogMapper;

    @Override
    public Page<SysLog> searchQuery(){

        Page<SysLog> page = new Page<>(1,5);
        page.setRecords(sysLogMapper.selectList(page, null));
        page.setTotal(sysLogMapper.selectCount(null));
        return page;
    }

    @Override
    public Boolean addSysLog(SysLog sysLog) {
        return sysLogMapper.insert(sysLog) == 1;
    }
}
