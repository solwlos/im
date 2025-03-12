package com.sol.admin.nettyWebsocket;

import io.netty.channel.Channel;
import io.netty.channel.ChannelId;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.AttributeKey;
import io.netty.util.concurrent.GlobalEventExecutor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author sol
 * @date 2023/12/20 11:29
 * @Version 1.0
 */
@Slf4j
@Component
public class NioWebSocketChannelPool {

    // 通道集合
    private final ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    // 用户id和通道id的映射
    private static final Map<String, ChannelId> map = new ConcurrentHashMap<>();

    /**
     * 新增一个客户端通道
     *
     * @param channel
     */
    public void addChannel(Channel channel) {
        channels.add(channel);
    }

    /**
     * 新增一个客户端连接 映射
     *
     * @param channelId
     * @param userId
     */
    public void addMap(ChannelId channelId, String userId) {
        map.put(userId, channelId);
    }

    /**
     * 通过用户id获取通道
     * @param userId
     */
    public Channel getChannelByUserId(String userId) {
        return channels.find(map.get(userId));
    }

    /**
     * 移除一个客户端连接通道
     */
    public void removeChannel(Channel channel) {
        channels.remove(channel);
    }


    /**
     * 获取在线客户端连接数量
     */
    public Integer count() {
        return channels.size();
    }

}
