package com.severnetty.sever;

import com.severnetty.sever.handler.MessageHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SeverApplication {

    public static void main(String[] args) {
        //创建两个线程组 boosGroup、workerGroup
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            //创建服务端的启动对象，设置参数
            ServerBootstrap bootstrap = new ServerBootstrap();
            //设置两个线程组boosGroup和workerGroup
            bootstrap.group(bossGroup, workerGroup)
                    //设置服务端通道实现类型
                    .channel(NioServerSocketChannel.class)
                    //设置线程队列得到连接个数
                    .option(ChannelOption.SO_BACKLOG, 128)
                    //设置保持活动连接状态
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    //允许共用端口
                    .childOption(ChannelOption.SO_REUSEADDR, true)
                    //使用匿名内部类的形式初始化通道对象
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            //给pipeline管道设置处理器
//                            socketChannel.pipeline().addLast(new MyServerHandler());
                            //websocket基于http协议 所以要有一个http编解码器
                            socketChannel.pipeline().addLast(new HttpServerCodec());
                            //对于一个大数据流读写的支持
                            socketChannel.pipeline().addLast(new ChunkedWriteHandler());
                            //对于http message进行一些聚合 生成FullHttpRequest huo FullHttpResponse
                            socketChannel.pipeline().addLast(new HttpObjectAggregator(1024*64));
//                            socketChannel.pipeline().addLast(new WebSocketServerProtocolHandler("/ws"));
                            socketChannel.pipeline().addLast(new MessageHandler());
//                            socketChannel.pipeline().addLast(new MyServerHandler());

//                            socketChannel.pipeline().addLast(new WebSocketServerProtocolHandler("/", true));
                            socketChannel.pipeline().addLast(new WebSocketServerProtocolHandler("/", null, true, 65536));
                        }
                    });//给workerGroup的EventLoop对应的管道设置处理器
            //绑定端口号，启动服务端
            ChannelFuture channelFuture = bootstrap.bind(8081).sync();
            //对关闭通道进行监听
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }

        SpringApplication.run(SeverApplication.class, args);
    }

}
