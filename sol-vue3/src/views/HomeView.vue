<script setup lang="ts">
import { ref } from "vue";
let localVideo = ref<any>();
let remoteVideo = ref<any>();
let localStream = ref<any>(null);
//默认不显示视频通话（需拨通后显示）
let showVideo = ref(false);

const initLocalStream = () => {
  navigator.mediaDevices
    .getUserMedia({
      audio: true,
      video: true,
    })
    .then((stream) => {
      startLink(stream);//获取成功后调用开始连接的函数
    })
    .catch((e) => {
      alert("getUserMedia() error:" + e.name);
    });
};

const startLink = (stream) => {
  // console.log("获取stream成功！");
  //本地画面预览
  localVideo.value.srcObject = stream;
  localStream.value = stream;
  
  //为彼此添加ice candidate
  localPc.onicecandidate = (e) => {
    remotePc.addIceCandidate(e.candidate);
  };
  remotePc.onicecandidate = (e) => {
    localPc.addIceCandidate(e.candidate);
  };
  
  //提供远端画面
  remotePc.ontrack = (e) => {
    remoteVideo.value.srcObject = e.streams[0];
    // console.log('test success');
  };
  
  //将音视频逐一添加到本地流
  localStream.value.getTracks().forEach((track) => {
    localPc.addTrack(track, localStream.value);
    // console.log('test success');
  });
  
  //设定offer选项
  let offerOptions = {
    offerToRecieveAudio: 1,
    offerToRecieveVideo: 1,
  };
  
  //创建本地连接的offer，用于媒体协商
  localPc.createOffer(offerOptions).then((desc) => {
    //创建成功后会返回一个本地描述，并在这里设置本地描述
    localPc.setLocalDescription(desc);
    // console.log('send desc to signal');
    // console.log('receive desc from signal');
    // 设置远程描述
    remotePc.setRemoteDescription(desc);
    //创建远端描述回复
    remotePc.createAnswer().then((desc) => {
    //对两个对象设置远程/本地'描述'
      remotePc.setLocalDescription(desc);
      // console.log('send desc to signal');
      // console.log('receive desc from signal');
      localPc.setRemoteDescription(desc);
    });
  });
};
</script>
<template lang="">
  <!-- 视频通话组件 -->
  <div class="video-set" v-if="showVideo">
  //video标签中muted启用静音，playsinline启用视频将内联（inline）播放，即在元素的播放区域内。
  //本地视频
    <video
      autoplay
      ref="localVideo"
      id="localVideo"
      muted
      playsinline
      class="local"
    ></video>
    //远端视频
    <video
      autoplay
      ref="remoteVideo"
      id="remoteVideo"
      playsinline
      class="remote"
    ></video>
  </div>
  <el-button @click="call">发起视频通话</el-button>
  <el-button @click="cancel">挂断</el-button>
</template>
<style lang="css" scoped>
.video-set {
  position: relative;
  video {
    width: 270px;
    height: 202.5px;
    background-color: #fff;
    border-radius: 10px;
  }
  .local {
    background-color: #fff;
    width: 90px;
    height: 120px;
    position: absolute;
    left: 7.5px;
    top: 7.5px;
    border: 2px solid gray;
    object-fit: cover;
    background-color: transparent;
  }
  .remote {
  }
}
</style>