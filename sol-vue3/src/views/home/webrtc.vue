<template>
    <el-container class="rtc-container">
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
                        id="local"
                        ref="localVideoRef" 
                        autoplay 
                        class="local-video"
                    ></video>

                    <!-- 远程视频
                    <video 
                        ref="remoteVideoRef" 
                        autoplay 
                        class="remote-video"
                        v-if="remoteStream"
                    ></video> -->
                </div>
            </el-main>
        </el-container>
    </el-container>
</template>
<script setup lang="ts">
import { ref, onMounted, onUnmounted } from 'vue';
import type { User } from '@/types/user'
import { useUserStore } from '@/stores/user'
// WebRTC 相关类型定义
const user  = useUserStore().userInfo
// 接收父组件传递的 worker
const props  = defineProps({
  worker: {
    type: Object,
    required: true
  },
  users:[]
})

type OfferData = {
    to: number | string;
    from: number | string;
    sdp: RTCSessionDescriptionInit;
};

type AnswerData = RTCSessionDescriptionInit;

type IceCandidateData = {
    to: number;
    candidate: RTCIceCandidateInit;
};

// WebRTC 相关
const localStream = ref<MediaStream | null>(null);
const remoteStream = ref<MediaStream | null>(null);
const peerConnection = ref<RTCPeerConnection | null>(null);
// 选中回话消息的 用户 id
const remoteUserId = ref<number | string | null>("1");
const remoteUserName = ref<string>('');

// 视频元素引用
const localVideoRef = ref<HTMLVideoElement | null>(null);
const remoteVideoRef = ref<HTMLVideoElement | null>(null);

// 获取本地媒体流
const getLocalMediaStream = async (): Promise<void> => {
    try {
        localStream.value = await navigator.mediaDevices.getUserMedia({
            video: true,
            audio: true
        });
        if (localVideoRef.value) {
            localVideoRef.value.srcObject = localStream.value;
        }


    } catch (err) {
        console.error('媒体设备访问失败:', err);
    }
};

// 创建 PeerConnection
const createPeerConnection = (): void => {
    peerConnection.value = new RTCPeerConnection({
        iceServers: [{ urls: 'stun:stun.l.google.com:19302' }]
    });
};

// 处理 ICE 候选
const handleIceCandidate = (event: RTCPeerConnectionIceEvent, userId: number | string): void => {
    if (event.candidate) {
        props.worker.emit('ice-candidate', {
            to: userId,
            candidate: event.candidate
        });
    }
};

// 处理远程流
const handleRemoteStream = (event: RTCTrackEvent): void => {
    remoteStream.value = event.streams[0];
    if (remoteVideoRef.value) {
        remoteVideoRef.value.srcObject = remoteStream.value;
    }
};

// 连接到用户
const connectToUser = async (user: User): Promise<void> => {
    if (remoteUserId.value) return; // 已有通话

    createPeerConnection();

    // 本地流添加到 PeerConnection
    if (localStream.value) {
        localStream.value.getTracks().forEach((track: MediaStreamTrack) => {
            if (peerConnection.value) {
                peerConnection.value.addTrack(track, localStream.value as any);
            }
        });
    }

    try {
        // 创建 Offer
        if (peerConnection.value) {
            const offer = await peerConnection.value.createOffer();
            await peerConnection.value.setLocalDescription(offer);

            // 发送信令
            // socket.emit('offer', {
            //     to: user.id,
            //     from: 123, // 当前用户ID
            //     sdp: offer
            // });

            remoteUserId.value = user.id;
            remoteUserName.value = user.username;
            // user.connected = true;

            // 监听 ICE 候选
            peerConnection.value.onicecandidate = (event: RTCPeerConnectionIceEvent) => {
                handleIceCandidate(event, user.id);
            };

            // 监听远程流
            peerConnection.value.ontrack = handleRemoteStream;
        }
    } catch (err) {
        console.error('创建 Offer 失败:', err);
    }
};

// 处理 Offer
const handleOffer = async (data: OfferData): Promise<void> => {
    if (remoteUserId.value) return; // 已有通话

    createPeerConnection();

    if (localStream.value) {
        localStream.value.getTracks().forEach((track: MediaStreamTrack) => {
            if (peerConnection.value) {
                peerConnection.value.addTrack(track, localStream.value as any);
            }
        });
    }

    try {
        if (peerConnection.value) {
            await peerConnection.value.setRemoteDescription(data.sdp);
            const answer = await peerConnection.value.createAnswer();
            await peerConnection.value.setLocalDescription(answer);

            // socket.emit('answer', {
            //     to: data.from,
            //     sdp: answer
            // });

            remoteUserId.value = data.from;
            // remoteUserName.value = users.value.find(u => u.id === data.from).name;

            peerConnection.value.ontrack = handleRemoteStream;
        }
    } catch (err) {
        console.error('处理 Offer 失败:', err);
    }
};

// 处理 Answer
const handleAnswer = async (sdp: AnswerData): Promise<void> => {
    if (peerConnection.value) {
        try {
            await peerConnection.value.setRemoteDescription(sdp);
        } catch (err) {
            console.error('处理 Answer 失败:', err);
        }
    }
};

// 处理 ICE 候选
const handleIceCandidateReceived = (candidate: IceCandidateData['candidate']): void => {
    if (peerConnection.value) {
        try {
            peerConnection.value.addIceCandidate(new RTCIceCandidate(candidate));
        } catch (err) {
            console.error('添加 ICE 候选失败:', err);
        }
    }
};

// 挂断电话
const hangUp = (): void => {
    if (peerConnection.value) {
        peerConnection.value.close();
        peerConnection.value = null;
    }
    remoteStream.value = null;
    remoteUserId.value = null;
    localStream.value = null;
    // users.value.forEach(user => user.connected = false);
};
onMounted(async () => {
    await getLocalMediaStream();

    // 监听信令事件
    // socket.on('offer', handleOffer);
    // socket.on('answer', handleAnswer);
    // socket.on('ice-candidate', handleIceCandidateReceived);
    // if (localStream.value) {
    //     localStream.value.getTracks().forEach((track: MediaStreamTrack) => track.stop());
    // }
});

onUnmounted(() => {
    if (localStream.value) {
        localStream.value.getTracks().forEach((track: MediaStreamTrack) => track.stop());
    }
    // if (socket) socket.disconnect();
});
</script>    
<!-- <script setup lang="ts">
import { ref, onMounted, onUnmounted, watch } from 'vue';
// import { io } from 'socket.io-client'; // 信令服务器

// WebRTC 相关
const localStream = ref(null);
const remoteStream = ref(null);
const peerConnection = ref(null);
const remoteUserId = ref(null);
const remoteUserName = ref('');

// 视频元素引用
const localVideoRef = ref(null);
const remoteVideoRef = ref(null);

// 信令服务器连接
// const socket = io('ws://localhost:3000');

// 获取本地媒体流
const getLocalMediaStream = async () => {
    try {
        localStream.value = await navigator.mediaDevices.getUserMedia({
            video: true,
            audio: true
        });
        if (localVideoRef.value) {
            localVideoRef.value.srcObject = localStream.value;
        }
    } catch (err) {
        console.error('媒体设备访问失败:', err);
    }
};

// 创建 PeerConnection
const createPeerConnection = () => {
    peerConnection.value = new RTCPeerConnection({
        iceServers: [{ urls: 'stun:stun.l.google.com:19302' }]
    });
};

// 处理 ICE 候选
const handleIceCandidate = (event, userId) => {
    if (event.candidate) {
        // socket.emit('ice-candidate', {
        //     to: userId,
        //     candidate: event.candidate
        // });
    }
};

// 处理远程流
const handleRemoteStream = (event) => {
    remoteStream.value = event.streams[0];
    if (remoteVideoRef.value) {
        remoteVideoRef.value.srcObject = remoteStream.value;
    }
};

// 连接到用户
const connectToUser = async (user) => {
    if (remoteUserId.value) return; // 已有通话

    createPeerConnection();

    // 本地流添加到 PeerConnection
    if (localStream.value) {
        localStream.value.getTracks().forEach(track => {
            peerConnection.value.addTrack(track, localStream.value);
        });
    }

    try {
        // 创建 Offer
        const offer = await peerConnection.value.createOffer();
        await peerConnection.value.setLocalDescription(offer);

        // 发送信令
        // socket.emit('offer', {
        //     to: user.id,
        //     from: 123, // 当前用户ID
        //     sdp: offer
        // });

        remoteUserId.value = user.id;
        remoteUserName.value = user.name;
        // user.connected = true;

        // 监听 ICE 候选
        peerConnection.value.onicecandidate = (event) => {
            handleIceCandidate(event, user.id);
        };

        // 监听远程流
        peerConnection.value.ontrack = handleRemoteStream;
    } catch (err) {
        console.error('创建 Offer 失败:', err);
    }
};

// 处理 Offer
const handleOffer = async (data) => {
    if (remoteUserId.value) return; // 已有通话

    createPeerConnection();

    if (localStream.value) {
        localStream.value.getTracks().forEach(track => {
            peerConnection.value.addTrack(track, localStream.value);
        });
    }

    try {
        await peerConnection.value.setRemoteDescription(data.sdp);
        const answer = await peerConnection.value.createAnswer();
        await peerConnection.value.setLocalDescription(answer);

        // socket.emit('answer', {
        //     to: data.from,
        //     sdp: answer
        // });

        remoteUserId.value = data.from;
        // remoteUserName.value = users.value.find(u => u.id === data.from).name;

        peerConnection.value.ontrack = handleRemoteStream;
    } catch (err) {
        console.error('处理 Offer 失败:', err);
    }
};

// 处理 Answer
const handleAnswer = async (sdp) => {
    if (peerConnection.value) {
        try {
            await peerConnection.value.setRemoteDescription(sdp);
        } catch (err) {
            console.error('处理 Answer 失败:', err);
        }
    }
};

// 处理 ICE 候选
const handleIceCandidateReceived = (candidate) => {
    if (peerConnection.value) {
        try {
            peerConnection.value.addIceCandidate(new RTCIceCandidate(candidate));
        } catch (err) {
            console.error('添加 ICE 候选失败:', err);
        }
    }
};

// 挂断电话
const hangUp = () => {
    if (peerConnection.value) {
        peerConnection.value.close();
        peerConnection.value = null;
    }
    remoteStream.value = null;
    remoteUserId.value = null;
    // users.value.forEach(user => user.connected = false);
};

onMounted(async () => {
    await getLocalMediaStream();

    // 监听信令事件
    // socket.on('offer', handleOffer);
    // socket.on('answer', handleAnswer);
    // socket.on('ice-candidate', handleIceCandidateReceived);
});

onUnmounted(() => {
    if (localStream.value) {
        localStream.value.getTracks().forEach(track => track.stop());
    }
    // if (socket) socket.disconnect();
});
</script> -->

<style scoped>
.rtc-container {
    width: calc(100% - 20px); /* 确保视频宽度减去内边距 */
    margin-top: 10px; /* 视频区域顶部间距 */
    display: flex;
    flex-direction: column;
    min-height: 70vh;
    margin: 0;
    padding: 0;
    background: #f8f9fa;
}

.call-header {
    background: #fff;
    padding: 1.5rem;
    border-bottom: 1px solid #ebedf0;
}

.call-main {
    /* flex: 1; */
    display: flex;
    justify-content: center;
    /* align-items: center; */
    background: #fff;
}

.video-container {
    position: relative;
    /* display: flex; */
    /* align-items: center; */
    justify-content: center;
    width: 90%;
    max-width: 100%;
    min-height: 400px;
    height: 40%;
    border-radius: 1rem;
    overflow: hidden;
    box-shadow: 0 0.5rem 1.5rem rgba(0, 0, 0, 0.15);
}

.local-video {
    position: absolute;
    top: 2%;
    right: 2%;
    width: 20%;
    max-width: 250px;
    height: auto;
    aspect-ratio: 16/9;
    border: 4px solid #fff;
    border-radius: 1rem;
    box-shadow: 0 0.25rem 0.5rem rgba(0, 0, 0, 0.2);
    object-fit: cover;
    transform: scale(1);
    transition: transform 0.3s ease;
}

.remote-video {
    flex: 1;
    min-width: 300px;
    height: 80vh;
    object-fit: cover;
    border-radius: 1rem;
}

/* 响应式设计 */
@media (max-width: 768px) {
    .local-video {
        width: 30%;
        max-width: 180px;
        top: 3%;
        right: 3%;
    }

    .remote-video {
        height: 70vh;
    }
}

/* 动画效果 */
.local-video:hover {
    transform: scale(1.05);
}

/* 状态提示 */
.no-remote {
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
    color: #666;
    font-size: 1.2em;
    font-weight: 500;
}
</style>