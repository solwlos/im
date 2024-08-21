package com.sol.admin.common.util;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

@Slf4j
public class IPUtil {

    /**
     * 获取真实ip地址,不返回内网地址
     * @return
     */
    public static String getIpAddr(HttpServletRequest request) {
        try {
            String ip = request.getHeader("X-Real-IP");
            if(!StringUtils.isBlank(ip) && !"unknown".equalsIgnoreCase(ip))
                return ip;
            ip = request.getHeader("X-Forwarded-For");
            if(!StringUtils.isBlank(ip) && !"unknown".equalsIgnoreCase(ip))
            {
                int index = ip.indexOf(',');
                if(index != -1)
                    return ip.substring(0, index);
                else
                    return ip;
            }
            if(ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip))
                ip = request.getHeader("Proxy-Client-IP");
            if(ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip))
                ip = request.getHeader("WL-Proxy-Client-IP");
            if(ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip))
                ip = request.getHeader("HTTP_CLIENT_IP");
            if(ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip))
                ip = request.getHeader("HTTP_X_FORWARDED_FOR");
            if(ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip))
                ip = request.getRemoteAddr();
            return ip;
        } catch (Exception e) {
            log.error("获取客户端ip异常", e);
            return "";
        }
    }

}