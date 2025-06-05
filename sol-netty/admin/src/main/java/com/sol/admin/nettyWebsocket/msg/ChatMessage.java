package com.sol.admin.nettyWebsocket.msg;


import lombok.Data;

@Data
public class ChatMessage {
    //消息id
//    private String id;
    //发送者id
    private String fromId;
    //接收者id
    private String destId;
    //消息类型 1:文本消息 2:图片消息 3:语音消息 4:视频消息 5:文件消息
    private String msgType;
    // 消息内容
    private Object msgBody;

    // 消息范围 0:系统公告 1:单聊 2:群聊
    private Integer messageRange;
}
