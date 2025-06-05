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

<script setup lang="ts">
import { FolderOpened,VideoCamera } from '@element-plus/icons-vue'
import { ref } from 'vue'
import { useUserStore } from '@/stores/user'
import { msgStore } from '@/stores/msgStore'
import type { Msg } from '@/types/msg'

// 接收父组件传递的 worker
const props  = defineProps({
  worker: {
    type: SharedWorker,
    required: true
  },
  activeUser:{
    type: String,
    required: true
  }
})


// 直接使用字符串字面量定义事件
// 定义 sendData 事件，接受一个字符串参数
const emits = defineEmits<{
    (e: string, data: boolean): void;
}>();

// const isShowWebrtc = ref<boolean>(false)

const user = useUserStore()
const msgArray = msgStore().historymsg
const textarea = ref('')
// const msgArray = ref([])


function videoClick(){
    emits('isShow', true);
}

// 发送消息到 WebSocket
const sendMessage = () => {
    if (props.worker && textarea.value) {
        // 组装消息
        const sendMsg = {
            msgBody: textarea.value,
            fromId: user.userInfo.id, // 发送
            destId: "0", // 接受
            msgType: "1",
            messageRange: "1"
        } as Msg
        console.log(sendMsg)
        props.worker.port.postMessage({ command: 'send', data: JSON.stringify(sendMsg) })
        // 添加消息到历史记录数组
        msgArray.push({ type: 'sent', data: sendMsg })
        textarea.value = '' // 清空输入框
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
