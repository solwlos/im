// 定义消息类型
interface Message {
    type: string;
    data: Msg;
}
interface Msg {
    destId: string
    fromId: string
    messageRange: string
    msgBody: string
    msgType: string
}
interface Reply{
    id: string,
    name: string,
    lastMessage: string,
    unread: number,
    time: string | Date,
    avatar: string
}

export type { Message,Msg,Reply};