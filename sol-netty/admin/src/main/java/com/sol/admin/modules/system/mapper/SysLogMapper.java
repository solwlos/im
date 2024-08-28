package com.sol.admin.modules.system.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sol.admin.modules.base.EntitySearchQuery;
import com.sol.admin.modules.system.entity.SysLog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 系统日志 Mapper 接口
 * </p>
 *
 * @author sol
 * @since 2024-08-21
 */
public interface SysLogMapper extends BaseMapper<SysLog> {

    default Page<SysLog> searchQuery(EntitySearchQuery<SysLog> query){

        Page<SysLog> page = new Page<>(query.getPage().getPageNum(),query.getPage().getPageSize());
        QueryWrapper<SysLog> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(SysLog::getIp,query.getEntity().getIp());
        return selectPage(page,queryWrapper);
    }

}
