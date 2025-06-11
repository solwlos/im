// 定义消息类型
interface Message {
    type: string;
    data: Msg;
}
interface Msg {
    destId: string
    fromId: string
    messageRange: string
    msgBody: any
    msgType: string
}
interface Reply{
    id: string,
    name: string,
    lastMessage: string,
    type: number,
    unread: number,
    time: string | Date,
    avatar: string
}
interface ChatObject{
    type:number,    // 0: 单聊，1：群聊
    name:string,    
    status:number   // 0: 离线，1: 在线
}

export type { Message,Msg,Reply,ChatObject};