<!-- src/views/VideoCall.vue -->
<template>
    <el-container class="rtc-container">
      <!-- 左侧用户列表 -->
      <el-aside width="280px">
        <el-card class="user-list-card">
          <el-input
            v-model="searchText"
            placeholder="搜索用户"
            clearable
            class="mb-16"
          />
          <el-scrollbar class="user-scroll">
            <div class="user-list">
              <el-card
                v-for="user in filteredUsers"
                :key="user.id"
                class="user-item"
                @click="connectToUser(user)"
                :shadow="user.id === remoteUserId ? 'always' : 'hover'"
              >
                <el-avatar :src="user.avatar" size="large" />
                <div class="user-name">{{ user.name }}</div>
                <div class="status">
                  {{ user.connected ? '在线 · 通话中' : '在线' }}
                </div>
              </el-card>
            </div>
          </el-scrollbar>
        </el-card>
      </el-aside>
  
      <!-- 右侧通话区域 -->
      <el-container>
        <el-header class="call-header">
          <h3 v-if="remoteUserId">
            正在与 {{ remoteUserName }} 通话
            <el-button
              type="danger"
              size="small"
              @click="hangUp"
              class="ml-16"
            >
              挂断
            </el-button>
          </h3>
        </el-header>
        
        <el-main class="call-main">
          <div class="video-container">
            <!-- 本地视频 -->
            <video 
              ref="localVideo" 
              autoplay 
              muted 
              class="local-video"
            ></video>
            
            <!-- 远程视频 -->
            <video 
              ref="remoteVideo" 
              autoplay 
              class="remote-video"
              v-if="remoteStream"
            ></video>
          </div>
        </el-main>
      </el-container>
    </el-container>
  </template>
  
  <script setup>
  import { ref, onMounted, onUnmounted, watch } from 'vue'
//   import { io } from 'socket.io-client' // 信令服务器
  
  // 模拟用户列表（实际应从后端获取）
  const users = ref([
    { id: 1, name: 'Alice', avatar: 'https://via.placeholder.com/100', connected: false },
    { id: 2, name: 'Bob', avatar: 'https://via.placeholder.com/100', connected: false },
    { id: 3, name: 'Charlie', avatar: 'https://via.placeholder.com/100', connected: false }
  ])
  
  const searchText = ref('')
//   const filteredUsers = computed(() => 
//     users.value.filter(user => 
//       user.name.includes(searchText.value)
//     )
//   )
  
  // WebRTC 相关
  const localStream = ref(null)
  const remoteStream = ref(null)
  const peerConnection = ref(null)
  const remoteUserId = ref(null)
  const remoteUserName = ref('')
  
  // 信令服务器连接
  const socket = io('ws://localhost:3000')
  
  onMounted(async () => {
    // 获取本地媒体流
    try {
      localStream.value = await navigator.mediaDevices.getUserMedia({
        video: true,
        audio: true
      })
      const localVideo = $refs.localVideo
      localVideo.srcObject = localStream.value
    } catch (err) {
      console.error('媒体设备访问失败:', err)
    }
  
    // 监听信令事件
    socket.on('offer', handleOffer)
    socket.on('answer', handleAnswer)
    socket.on('ice-candidate', handleIceCandidate)
  })
  
  const connectToUser = async (user) => {
    if (remoteUserId.value) return // 已有通话
  
    // 创建 PeerConnection
    peerConnection.value = new RTCPeerConnection({
      iceServers: [{ urls: 'stun:stun.l.google.com:19302' }]
    })
  
    // 本地流添加到 PeerConnection
    localStream.value.getTracks().forEach(track => {
      peerConnection.value.addTrack(track, localStream.value)
    })
  
    // 创建 Offer
    const offer = await peerConnection.value.createOffer()
    await peerConnection.value.setLocalDescription(offer)
  
    // 发送信令
    socket.emit('offer', {
      to: user.id,
      from: 123, // 当前用户ID
      sdp: offer
    })
  
    remoteUserId.value = user.id
    remoteUserName.value = user.name
    user.connected = true
  
    // 监听 ICE 候选
    peerConnection.value.onicecandidate = (event) => {
      if (event.candidate) {
        socket.emit('ice-candidate', {
          to: user.id,
          candidate: event.candidate
        })
      }
    }
  
    // 监听远程流
    peerConnection.value.ontrack = (event) => {
      remoteStream.value = event.streams[0]
      const remoteVideo = $refs.remoteVideo
      remoteVideo.srcObject = remoteStream.value
    }
  }
  
  const handleOffer = async (data) => {
    if (remoteUserId.value) return // 已有通话
  
    peerConnection.value = new RTCPeerConnection({
      iceServers: [{ urls: 'stun:stun.l.google.com:19302' }]
    })
  
    localStream.value.getTracks().forEach(track => {
      peerConnection.value.addTrack(track, localStream.value)
    })
  
    await peerConnection.value.setRemoteDescription(data.sdp)
    const answer = await peerConnection.value.createAnswer()
    await peerConnection.value.setLocalDescription(answer)
  
    socket.emit('answer', {
      to: data.from,
      sdp: answer
    })
  
    remoteUserId.value = data.from
    remoteUserName.value = users.value.find(u => u.id === data.from).name
  
    peerConnection.value.ontrack = (event) => {
      remoteStream.value = event.streams[0]
    }
  }
  
  const handleAnswer = async (sdp) => {
    if (peerConnection.value) {
      await peerConnection.value.setRemoteDescription(sdp)
    }
  }
  
  const handleIceCandidate = (candidate) => {
    if (peerConnection.value) {
      peerConnection.value.addIceCandidate(new RTCIceCandidate(candidate))
    }
  }
  
  const hangUp = () => {
    if (peerConnection.value) {
      peerConnection.value.close()
      peerConnection.value = null
    }
    remoteStream.value = null
    remoteUserId.value = null
    users.value.forEach(user => user.connected = false)
  }
  
  onUnmounted(() => {
    if (localStream.value) {
      localStream.value.getTracks().forEach(track => track.stop())
    }
    if (socket) socket.disconnect()
  })
  </script>
  
  <style scoped>
  .rtc-container {
    height: 90vh;
    background: #f8f9fa;
  }
  
  .user-list-card {
    height: 100%;
    border-radius: 0;
  }
  
  .user-scroll {
    height: calc(100% - 80px);
  }
  
  .user-item {
    margin: 12px 0;
    cursor: pointer;
    transition: all 0.3s;
  }
  
  .user-item:hover {
    transform: translateX(4px);
    box-shadow: 2px 4px 12px rgba(0,0,0,0.1);
  }
  
  .call-header {
    background: #fff;
    padding: 16px;
    border-bottom: 1px solid #ebedf0;
  }
  
  .call-main {
    display: flex;
    justify-content: center;
    align-items: center;
    background: #fff;
  }
  
  .video-container {
    position: relative;
    width: 800px;
    height: 600px;
  }
  
  .local-video {
    position: absolute;
    top: 20px;
    right: 20px;
    width: 180px;
    height: 135px;
    border: 3px solid #fff;
    border-radius: 8px;
    box-shadow: 0 2px 8px rgba(0,0,0,0.2);
    object-fit: cover;
  }
  
  .remote-video {
    width: 100%;
    height: 100%;
    object-fit: cover;
    border-radius: 8px;
  }
  </style>