package com.sol.admin.modules.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sol.admin.modules.base.EntitySearchQuery;
import com.sol.admin.modules.system.entity.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;
import jakarta.servlet.ServletRequest;
import org.springframework.http.ResponseEntity;

import java.util.Map;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author sol
 * @since 2024-02-20
 */
public interface SysUserService {

    ResponseEntity<Map<String,Object>> sysUserLogin(ServletRequest request , String username, String password);

    Boolean addUser(SysUser sysUser);

    Boolean updateUser(SysUser sysUser);

    Page<SysUser> searchQuery(EntitySearchQuery<SysUser> query);
}
