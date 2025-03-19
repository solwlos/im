<template>
  <div class="chat-container">
    <el-row>
      <el-col :span="1"></el-col>

      <el-col :span="7">
        <UserLink />
      </el-col>

      <el-col :span="14">
        <Chat :worker="worker" />
        <el-dialog 
          v-model="isShowWebrtc" 
          center 
          custom-class="webrtc-dialog"
        >
          <Webrtc :worker="worker" @isShowWebrtc="isShow"/>
        </el-dialog>
      </el-col>
    </el-row>
  </div>
</template>

<script setup lang="ts">
import { onMounted, onUnmounted, ref } from 'vue'
import SharedWorker from '@/websocket/work.js?sharedworker'
import { useUserStore } from '@/stores/user'
import { ElRow, ElCol } from 'element-plus'
import { msgStore } from '@/stores/msgStore'

import Chat from './chat.vue'
import UserLink from './userLink.vue'
import Webrtc from './webrtc.vue'

const user = useUserStore()
const isShowWebrtc = ref(false); 
const msgArray = msgStore().historymsg

function isShow(isShowWebrtc: boolean){
  isShowWebrtc = isShowWebrtc
}


// 声明 worker 并初始化
const worker = new SharedWorker()
// 启动消息通道
worker.port.start() 

worker.port.onmessage = function (event) {
    const message = event.data
    if (message.type === 'status') {
        console.log('Status:', message.data)
    } else if (message.type === 'message') {
        // 解析 msg.data 为 JavaScript 对象
        const msgObj = JSON.parse(message.data)
        // 添加接收到的消息到历史记录数组
        msgArray.push({ type: 'received', data: msgObj })
    } else if (message.type === 'error') {
        console.error('WebSocket error:', message.data)
    }
}

onMounted(() => {
    console.log('Attempting to connect to WebSocket...')
    let url = 'ws://127.0.0.1:8081?token=' + user.token + '&userId=' + user.userInfo.id;
    if (worker) {
        worker.port.postMessage({ command: 'connect', url: url })
    }
})

onUnmounted(() => {
    console.log('Disconnecting from WebSocket...')
    if (worker) {
        worker.port.postMessage({ command: 'disconnect' })
    }
})


</script>

<style scoped>
.chat-container {
  padding: 20px;
  width: 1024px;
  border: 1px solid #ccc;
}

.webrtc-dialog .el-dialog__body {
  padding: 10px; /* 调整对话框内容区内边距 */
}

/* 假设 WebRTC 组件内部视频容器的类名为 video-container */
.webrtc-dialog {
  width: calc(100% - 20px); /* 确保视频宽度减去内边距 */
  margin-top: 10px; /* 视频区域顶部间距 */
}
</style>    