package com.sol.admin.modules.base.aspect;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.SystemClock;
import com.sol.admin.common.util.IPUtil;
import com.sol.admin.modules.base.annontation.ApiLog;
import com.sol.admin.modules.system.entity.SysLog;
import com.sol.admin.modules.system.service.SysLogService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;
import java.util.Arrays;

@Aspect
@Component
public class SysLogAspect {

//    @Autowired
    @Resource
    HttpServletRequest request;

    @Autowired
    SysLogService sysLogService;

    @Around("@annotation(apiLog)")
    public Object around(ProceedingJoinPoint joinPoint, ApiLog apiLog) throws Throwable {
        long beginTime = SystemClock.now();
        //执行方法
        Object result = joinPoint.proceed();
        //执行时长(毫秒)
        long time = SystemClock.now() - beginTime;

        SysLog sysLog = new SysLog();
        //注解上的描述
        if (apiLog != null) sysLog.setOperation(apiLog.value());

        //请求的方法名
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        sysLog.setMethod(className + "." + methodName + "()");

        //请求的参数 过滤 HttpServletRequest 和 HttpServletResponse 类型
        Object[] args = Arrays.stream(joinPoint.getArgs())
                .filter(item -> !(item instanceof HttpServletRequest ||
                        item instanceof HttpServletResponse ||
                        item instanceof MultipartFile ||
                        item instanceof MultipartFile[]))
                .toArray();
        String params = JSON.toJSONString(args);
        sysLog.setParams(params);
        //设置IP地址
        sysLog.setIp(IPUtil.getIpAddr(request));

        // 从Spring 的上下文获取用户名
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        sysLog.setUsername(authentication.getName());
        sysLog.setTime(time);
        sysLog.setCreatedTime(new Timestamp(System.currentTimeMillis()));
        //保存系统日志
        sysLogService.save(sysLog);
        return result;
    }
}
