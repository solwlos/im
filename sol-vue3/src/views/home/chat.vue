若要让 `FolderOpened` 和 `VideoCamera` 图标处于同一水平线上，你可以对 `input-section` 类的样式加以调整。当前 `input-section` 类已经使用了 `display: flex`，不过需要确保其内部元素能正确布局。以下是修改后的代码：

```vue
<template>
    <div class="chat-container">
        <!-- 消息显示区域 -->
        <div class="message-display">
            <div v-for="(msg, index) in msgArray" :key="index" :class="{'sent-message': msg.type === 'sent', 'received-message': msg.type === 'received'}">
                <div class="message-bubble">
                    <span v-if="msg.type === 'sent'"> {{ user.userInfo.id }} :: {{ user.userInfo.username }}</span>
                    <span v-if="msg.type === 'received'">From {{ msg.data.fromId }}</span>
                    <p> 1 {{ msg.data.msgBody }}</p>
                </div>
            </div>
        </div>
        <!-- 输入框和发送按钮区域 -->
        <div class="input-section" style="padding-left: 20px;">

            <el-upload
                ref="uploadRef"
                class="upload-demo"
                action="https://run.mocky.io/v3/9d059bf9-4660-45f2-925d-ce80ad6c4d15"
                :auto-upload="false"
            >
                <FolderOpened  style="width: 1.5em; height: 1.5em; margin-right: 8px" />
            </el-upload>
            
            <VideoCamera @Click="videoClick" style="width: 1.5em; height: 1.5em; margin-right: 8px" />
            
        </div>
        <!-- 输入框和发送按钮区域 -->
        <div class="input-section">
            <el-input
                v-model="textarea"
                type="textarea"
                placeholder="请输入消息"
                autosize
                maxlength="30"
                show-word-limit
                style="flex: 1; margin-right: 10px;"
            />
            <el-button @click="sendMessage">发送消息</el-button>
        </div>
    </div>
</template>

<script setup>
import { FolderOpened,VideoCamera } from '@element-plus/icons-vue'
import { onMounted, onUnmounted, ref } from 'vue'
import SharedWorker from '@/websocket/work.js?sharedworker'
import { useUserStore } from '@/stores/user'
import { msgStore } from '@/stores/msgStore'
const user = useUserStore()
const msgArray = msgStore().historymsg

const textarea = ref('')
// const msgArray = ref([])

let worker = new SharedWorker()
worker.port.start() // 启动消息通道

worker.port.onmessage = function (event) {
    const message = event.data
    if (message.type === 'status') {
        console.log('Status:', message.data)
    } else if (message.type === 'message') {
        console.log('WebSocket message:', message.data)

        // 解析 msg.data 为 JavaScript 对象
        const msgObj = JSON.parse(message.data)

        // 添加接收到的消息到历史记录数组
        msgArray.push({ type: 'received', data: msgObj })
    } else if (message.type === 'error') {
        console.error('WebSocket error:', message.data)
    }
}

// 发送消息到 WebSocket
const sendMessage = () => {
    if (worker && textarea.value) {
        // 组装消息
        const sendMsg = {
            msgBody: textarea.value,
            fromId: user.userInfo.id, // 发送
            destId: 0, // 接受
            msgType: 1,
            messageRange: 1
        }
        console.log(sendMsg)
        worker.port.postMessage({ command: 'send', data: JSON.stringify(sendMsg) })
        // 添加消息到历史记录数组
        msgArray.push({ type: 'sent', data: sendMsg })
        textarea.value = '' // 清空输入框
    }
}
function videoClick(){

}

onMounted(() => {
    console.log('Attempting to connect to WebSocket...')
    connectToWebSocket('ws://127.0.0.1:8081?token=' + user.token + '&userId=' + user.userInfo.id)
})

onUnmounted(() => {
    console.log('Disconnecting from WebSocket...')
    disconnectWebSocket()
})
const connectToWebSocket = (url) => {
    if (worker) {
        worker.port.postMessage({ command: 'connect', url: url })
    }
}

const disconnectWebSocket = () => {
    if (worker) {
        worker.port.postMessage({ command: 'disconnect' })
    }
}
</script>

<style scoped>
.chat-container {
    display: flex;
    flex-direction: column;
    min-height: 90vh;
    width: 90%; /* 宽度使用百分比，更具弹性 */
    max-width: 600px; /* 最大宽度限制 */
    margin: 0 auto; /* 水平居中 */
    padding: 20px;
    background-color: #f5f5f5;
    box-sizing: border-box;
}

.message-display {
    flex: 1;
    overflow-y: auto;
    padding: 15px;
    background-color: #fff;
    border-radius: 8px;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
    margin-bottom: 20px;
    display: flex;
    flex-direction: column;
}

.sent-message {
    display: flex;
    justify-content: flex-end;
    margin-bottom: 15px;
}

.received-message {
    display: flex;
    justify-content: flex-start;
    margin-bottom: 15px;
}

.message-bubble {
    max-width: 70%;
    padding: 12px 18px;
    border-radius: 20px;
    position: relative;
}

.sent-message .message-bubble {
    background-color: #dcf8c6;
    color: #333;
}

.received-message .message-bubble {
    background-color: #e5e5ea;
    color: #333;
}

.message-bubble span {
    font-size: 0.9em;
    font-weight: bold;
    display: block;
    margin-bottom: 5px;
}

.message-bubble p {
    margin: 0;
}

.input-section {
    display: flex;
    align-items: center;
    gap: 10px; /* 元素之间的间距 */
    /* 确保内部元素在同一水平线上 */
    flex-wrap: nowrap; 
    padding-bottom: 10px;
}

/* 确保 el-upload 内的图标正确显示 */
.upload-demo {
    display: flex;
    align-items: center;
}
</style>
