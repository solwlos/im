package com.sol.admin.nettyWebsocket.controller;


import com.sol.admin.nettyWebsocket.NioWebSocketChannelPool;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/server")
@Tag(name = "/server", description = "服务端管理")
public class ServerController {


    @Autowired
    private NioWebSocketChannelPool nioWebSocketChannelPool;

    @GetMapping("/count")
    @Operation(summary ="查看在线人数")
    public Integer count(){
        return nioWebSocketChannelPool.count();
    }

}
