package com.sol.admin.nettyWebsocket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author sol
 * @date 2023/12/20 13:10
 * @Version 1.0
 */
@Slf4j
@Component
public class NioWebSocketServer implements InitializingBean, DisposableBean {

    @Autowired
    private WebSocketProperties webSocketProperties;
    @Autowired
    private NioWebSocketChannelInitializer webSocketChannelInitializer;

    private EventLoopGroup bossGroup;
    private EventLoopGroup workGroup;
    private ChannelFuture channelFuture;

    @Override
    public void afterPropertiesSet() throws Exception {
        try {
            //创建两个线程组 boosGroup、workerGroup
            bossGroup = new NioEventLoopGroup(webSocketProperties.getBoss());
            workGroup = new NioEventLoopGroup(webSocketProperties.getWork());
            //创建服务端的启动对象，设置参数
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            //设置线程队列得到连接个数
            serverBootstrap.option(ChannelOption.SO_BACKLOG, 1024)
                    //设置两个线程组boosGroup和workerGroup
                    .group(bossGroup, workGroup)
                    //设置服务端通道实现类型
                    .channel(NioServerSocketChannel.class)
                    .localAddress(webSocketProperties.getPort())
                    //使用匿名内部类的形式初始化通道对象
                    .childHandler(webSocketChannelInitializer);
            //绑定端口号，启动服务端
            channelFuture = serverBootstrap.bind().sync();
        } finally {
            if (channelFuture != null && channelFuture.isSuccess()) {
                log.info("Netty server startup on port: {} (websocket) with context path '{}'", webSocketProperties.getPort(), webSocketProperties.getPath());
//                log.info("Netty server startup on port: {} (websocket) with context path ", webSocketProperties.getPort());
            } else {
                log.error("Netty server startup failed.");
                if (bossGroup != null)
                    bossGroup.shutdownGracefully().sync();
                if (workGroup != null)
                    workGroup.shutdownGracefully().sync();
            }
        }
    }

    @Override
    public void destroy() throws Exception {
        log.info("Shutting down Netty server...");
        if (bossGroup != null)
            bossGroup.shutdownGracefully().sync();
        if (workGroup != null)
            workGroup.shutdownGracefully().sync();
        if (channelFuture != null)
            channelFuture.channel().closeFuture().syncUninterruptibly();
        log.info("Netty server shutdown.");
    }
}

