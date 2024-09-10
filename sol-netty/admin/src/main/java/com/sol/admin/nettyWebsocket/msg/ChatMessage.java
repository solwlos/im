package com.sol.admin.nettyWebsocket.msg;


import lombok.Data;

@Data
public class ChatMessage {
    //消息id
    private String id;

    //发送者 fromId
    private String fromId;

    //接收者 destId
    private String destId;

    //消息体
    private String msgBody;

}
