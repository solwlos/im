package com.sol.admin.nettyWebsocket;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

/**
 * @author sol
 * @date 2023/12/20 11:28
 * @Version 1.0
 */
@Getter
@Setter
@Component
//@ConfigurationProperties(prefix = "chat.websocket")
public class WebSocketProperties {

    private Integer port = 8081; // 监听端口
//    private String path = "/ws"; // 请求路径
    private String path = "/"; // 请求路径
    private Integer boss = 2; // bossGroup线程数
    private Integer work = 2; // workGroup线程数

}
