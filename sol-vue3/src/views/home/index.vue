<template>
  <div class="about">
    <el-input
      type="textarea"
      placeholder="请输入"
      v-model="textarea"
      autosize
      maxlength="30"
      show-word-limit
    >
      {{ item }}
    </el-input>
    <el-button @click="sendMessage">Send Message</el-button>
  </div>
</template>

<script setup>
import { onMounted, onUnmounted, ref } from 'vue'
import SharedWorker from '@/websocket/work.js?sharedworker'

const textarea = ref('')

let worker = new SharedWorker()

worker.port.onmessage = function (event) {
  const message = event.data
  if (message.type === 'status') {
    console.log('Status:', message.data)
  } else if (message.type === 'message') {
    console.log('WebSocket message:', message.data)
  } else if (message.type === 'error') {
    console.error('WebSocket error:', message.data)
  }
}

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

// 发送消息到 WebSocket
const sendMessage = () => {
  if (worker && textarea.value) {
    worker.port.postMessage({ command: 'send', data: textarea.value })
    textarea.value = '' // 清空输入框
  }
}

onMounted(() => {
  console.log('Attempting to connect to WebSocket...')
  connectToWebSocket('ws://127.0.0.1:8081/')
})

onUnmounted(() => {
  console.log('Disconnecting from WebSocket...')
  disconnectWebSocket()
})
</script>

<style>
@media (min-width: 1024px) {
  .about {
    min-height: 100vh;
    display: flex;
    align-items: center;
  }
}
</style>
