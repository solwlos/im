package com.sol.admin.nettyWebsocket;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author sol
 * @date 2023/12/20 13:09
 * @Version 1.0
 */
@Component
public class NioWebSocketChannelInitializer extends ChannelInitializer<SocketChannel> {
    //初始化通道对象
    @Autowired
    private WebSocketProperties webSocketProperties;
    @Autowired
    private NioWebSocketHandler nioWebSocketHandler;

    @Override
    protected void initChannel(SocketChannel socketChannel) {
        socketChannel.pipeline()
                //给pipeline管道设置处理器
                .addLast(new HttpServerCodec())
                .addLast(new ChunkedWriteHandler())
                .addLast(new HttpObjectAggregator(8192))
                .addLast(nioWebSocketHandler)
                .addLast(new WebSocketServerProtocolHandler(webSocketProperties.getPath(), null, true, 65536));
    }
}

