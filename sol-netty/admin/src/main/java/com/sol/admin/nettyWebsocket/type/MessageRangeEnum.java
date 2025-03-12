package com.sol.admin.nettyWebsocket.type;


import lombok.Getter;

/**
 * @Author：sol
 * @Date： 2025/3/10
 */
@Getter
public enum MessageRangeEnum {

    SYSTEM(0, "系统通知"),// 广播
    CHAT(1, "单个用户通知"),// 点对点,聊天
    GROUP(2, "群组通知"),// 部分广播、这个需要记录每一个群组的用户
    ;
    private final Integer code;

    private final String msg;

    MessageRangeEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
