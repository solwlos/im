package com.sol.admin.nettyWebsocket.controller;


import com.groupdocs.viewer.Viewer;
import com.groupdocs.viewer.options.HtmlViewOptions;
import com.sol.admin.nettyWebsocket.NioWebSocketChannelPool;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/server")
@Tag(name = "/server", description = "服务端管理")
public class ServerController {


    @Autowired
    private NioWebSocketChannelPool nioWebSocketChannelPool;

    @GetMapping("/count")
    @Operation(summary ="查看在线人数")
    public Integer count(){
        System.out.println("在线人数："+nioWebSocketChannelPool.count());
        return nioWebSocketChannelPool.count();
    }
}
