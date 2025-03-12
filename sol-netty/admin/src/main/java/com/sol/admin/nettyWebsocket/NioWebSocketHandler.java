package com.sol.admin.nettyWebsocket;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sol.admin.common.constants.RedisKeys;
import com.sol.admin.common.util.RequestUriUtils;
import com.sol.admin.modules.system.dto.UserInfo;
import com.sol.admin.nettyWebsocket.msg.AckMessage;
import com.sol.admin.nettyWebsocket.msg.ChatMessage;
import com.sol.admin.nettyWebsocket.type.MessageRangeEnum;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.websocketx.*;
import io.swagger.v3.core.util.Json;
import jakarta.servlet.ServletException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author sol
 * @date 2023/12/20 13:07
 * @Version 1.0
 */
@Slf4j
@ChannelHandler.Sharable
@Component
public class NioWebSocketHandler extends SimpleChannelInboundHandler<WebSocketFrame> {

    @Autowired
    private NioWebSocketChannelPool webSocketChannelPool;
    @Autowired
    private WebSocketProperties webSocketProperties;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("1、客户端连接：{}", ctx.channel().id());
        webSocketChannelPool.addChannel(ctx.channel());
        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.info("2、客户端断开连接：{}", ctx.channel().id());
        webSocketChannelPool.removeChannel(ctx.channel());
        super.channelInactive(ctx);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {

        log.info("3、下一个");
        ctx.channel().flush();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, WebSocketFrame frame) {
        // 打印帧类型和原始数据
        log.info("4、WebSocket 帧类型: {}", frame.getClass().getSimpleName());
        // 根据请求数据类型进行分发处理
        if (frame instanceof PingWebSocketFrame) {
            log.info("客户端发送ping请求");
            pingWebSocketFrameHandler(ctx, (PingWebSocketFrame) frame);
        } else if (frame instanceof TextWebSocketFrame) {
            textWebSocketFrameHandler(ctx, (TextWebSocketFrame) frame);
        } else if (frame instanceof CloseWebSocketFrame) {
            log.info("客户端发送关闭请求");
            closeWebSocketFrameHandler(ctx, (CloseWebSocketFrame) frame);
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        log.info("5、客户端请求数据类型：{}", msg.getClass());
        log.info("客户端请求数据 msg：{}", Json.pretty(msg));
        log.info("客户端请求数据 ctx：{}", Json.pretty(ctx));
        if (msg instanceof FullHttpRequest) {
            fullHttpRequestHandler(ctx, (FullHttpRequest) msg);
        }
        super.channelRead(ctx, msg);
    }

    /**
     * 处理连接请求，客户端WebSocket发送握手包时会执行这一次请求
     * @param ctx
     * @param request
     */
    private void fullHttpRequestHandler(ChannelHandlerContext ctx, FullHttpRequest request) {
        String uri = request.uri();
        Map<String, String> params = RequestUriUtils.getParams(uri);
        String authHeader = params.get("token");
        // 第一次连接，判断是否有权限 TODO

        log.info("6、客户端请求参数：{}", params);
        if (params.containsKey("userId") && params.containsKey("token")){
            // 记录 用户ID 所对应的 ChannelId
            webSocketChannelPool.addMap(ctx.channel().id(), params.get("userId"));
        }
        // 判断请求路径是否跟配置中的一致
        if (webSocketProperties.getPath().equals(RequestUriUtils.getBasePath(uri)))
            // 因为有可能携带了参数，导致客户端一直无法返回握手包，因此在校验通过后，重置请求路径
            request.setUri(webSocketProperties.getPath());
        else
            ctx.close();
    }

    /**
     * 客户端发送断开请求处理
     * @param ctx
     * @param frame
     */
    private void closeWebSocketFrameHandler(ChannelHandlerContext ctx, CloseWebSocketFrame frame) {
        ctx.close();
    }

    /**
     * 创建连接之后，客户端发送的消息都会在这里处理
     * @param ctx
     * @param frame
     */
    private void textWebSocketFrameHandler(ChannelHandlerContext ctx, TextWebSocketFrame frame) {
//            log.info("客户端发送文本请求 {}", JSON.toJSONString(textWebSocketFrame.content()));
//            log.info("客户端发送文本请求 {}", textWebSocketFrame.text());

//            ByteBuf buffer = textWebSocketFrame.content();

//            // 读取数据
//            byte[] bytes = new byte[buffer.readableBytes()];
//            buffer.readBytes(bytes);
//            System.out.println(new String(bytes));
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            ChatMessage message = objectMapper.readValue(frame.text(), ChatMessage.class);

            log.info("发送消息：{}", message);
            // 判断是单聊、群聊、系统通知
            if (message.getMessageRange().equals(MessageRangeEnum.CHAT.getCode())){ // 单聊
                // 查找目标用户的 Channel
                Channel channel = webSocketChannelPool.getChannelByUserId(message.getDestId());
                if (channel == null){
                    log.error("目标用户不在线！！！");
                }else {
                    // 数据转发 给目标用户
                    channel.writeAndFlush(new TextWebSocketFrame(frame.text()));
                }
            }else if (message.getMessageRange().equals(MessageRangeEnum.GROUP.getCode())){ // 群聊



//                channel.writeAndFlush(new TextWebSocketFrame(message.getMsgBody()));
            }else if (message.getMessageRange().equals(MessageRangeEnum.SYSTEM.getCode())){ // 系统通知
//                channel.writeAndFlush(new TextWebSocketFrame(message.getMsgBody()));
            }
        }catch (IOException e){
            log.error("JSON 解析失败: ",e);
        }
        // 客户端发送过来的内容不进行业务处理，原样返回
//        ctx.channel().writeAndFlush(frame.retain());
    }

    /**
     * 处理客户端心跳包
     * @param ctx
     * @param frame
     */
    private void pingWebSocketFrameHandler(ChannelHandlerContext ctx, PingWebSocketFrame frame) {
        ctx.channel().writeAndFlush(new PongWebSocketFrame(frame.content().retain()));
    }
}

