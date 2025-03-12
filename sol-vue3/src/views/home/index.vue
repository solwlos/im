<template>
  <div class="chat-container">
    <el-row>
      <!-- 这里设置 UserLink 组件占 6 份，比例为 3:1 -->
      <el-col :span="7">
        <UserLink />
      </el-col>
      <!-- 这里设置 Chat 组件占 18 份，比例为 3:1 -->
      <el-col :span="14">
        <Chat />
      </el-col>
    </el-row>
  </div>
</template>

<script setup>
import { onMounted, onUnmounted, ref } from 'vue'
import SharedWorker from '@/websocket/work.js?sharedworker'
import { useUserStore } from '@/stores/user'
import Chat from './chat.vue'
import UserLink from './userLink.vue'
// 引入 Element Plus 的 Row 和 Col 组件
import { ElRow, ElCol } from 'element-plus'
</script>

<style scoped>
/* 你可以根据需要添加更多样式 */
.chat-container {
  padding: 20px;
  width: 1024px;
  border: 1px solid #ccc; /* 添加外边框，颜色为灰色，宽度为 1px */
}
</style>    