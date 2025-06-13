<template>
    <div class="chat-container">
        <!-- 消息显示区域 -->
        <div class="message-display">
            <div v-for="(msg, index) in msgArray" :key="index" :class="{'sent-message': msg.type === 'sent', 'received-message': msg.type === 'received'}">
                <div class="message-bubble">
                    <span v-if="msg.type === 'sent'"> {{ user.userInfo.id }} :: {{ user.userInfo.username }}</span>
                    <span v-if="msg.type === 'received'">From {{ msg.data.fromId }}</span>
                    <p class="message-text">{{ msg.data.msgBody }}</p>
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
import { FolderOpened, VideoCamera } from '@element-plus/icons-vue'
import { ref } from 'vue'
import { useUserStore } from '@/stores/user'
import { msgStore } from '@/stores/msgStore'
import type { Msg } from '@/types/msg'

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

const emits = defineEmits<{
    (e: string, data: boolean): void;
}>();

const user = useUserStore()
const msgArray = msgStore().historymsg
const textarea = ref('')

function videoClick(){
    emits('isShow', true);
}

const sendMessage = () => {
    if (props.worker && textarea.value) {
        const sendMsg = {
            msgBody: textarea.value,
            fromId: user.userInfo.id,
            destId: "0",
            msgType: "1",
            messageRange: "1"
        } as Msg
        console.log(sendMsg)
        props.worker.port.postMessage({ command: 'send', data: JSON.stringify(sendMsg) })
        msgArray.push({ type: 'sent', data: sendMsg })
        textarea.value = ''
    }
}
</script>

<style scoped>
.chat-container {
    display: flex;
    flex-direction: column;
    min-height: 90vh;
    width: 90%;
    max-width: 600px;
    margin: 0 auto;
    padding: 20px;
    background-color: #f5f5f5;
    box-sizing: border-box;
}

.message-display {
    flex: 1;
    overflow-y: auto; /* 关键：启用垂直滚动 */
    max-height: 70vh; /* 关键：设置最大高度，超过时出现滚动条 */
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
    word-break: break-all; /* 关键：允许单词内换行 */
    overflow-wrap: break-word; /* 关键：长单词或URL换行 */
}

.message-text {
    margin: 0;
    white-space: normal; /* 关键：允许文本自动换行 */
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

.input-section {
    display: flex;
    align-items: center;
    gap: 10px;
    flex-wrap: nowrap;
    padding-bottom: 10px;
}

.upload-demo {
    display: flex;
    align-items: center;
}
</style>