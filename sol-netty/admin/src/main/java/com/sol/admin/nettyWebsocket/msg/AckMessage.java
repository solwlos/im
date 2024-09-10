package com.sol.admin.nettyWebsocket.msg;


import lombok.Data;

@Data
public class AckMessage {

    //消息id
    private String id;
    //发送者id
    private String fromId;
    //接收者id
    private String destId;
    //消息类型
    private String msgType;
    //确认的消息id
    private String ackMsgId;

}
