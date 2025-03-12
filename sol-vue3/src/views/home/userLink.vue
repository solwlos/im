<template>
    <el-scrollbar height="600px">
      <div class="group-list">
        <div 
          v-for="group in groupList" 
          :key="group.id" 
          class="group-item"
          :class="{ 'active': activeGroupId === group.id }"
          @click="selectGroup(group.id)"
        >
          <!-- 群组头像 -->
          <el-avatar :src="group.avatar" size="medium" />
  
          <!-- 群组信息 -->
          <div class="group-info">
            <div class="group-name">{{ group.name }}</div>
            <div class="last-message">{{ group.lastMessage }}</div>
          </div>
  
          <!-- 右侧信息 -->
          <div class="right-info">
            <div class="time">{{ group.time }}</div>
            <el-badge 
              v-if="group.unread > 0" 
              :value="group.unread" 
              class="unread-badge" 
            />
          </div>
        </div>
      </div>
    </el-scrollbar>
  </template>
  
  <script setup>
  import { ref } from 'vue';
  
  const activeGroupId = ref(null);
//   const groupList = ref([...]); // 使用上述数据结构
  const groupList = ref([
  {
    id: 1,
    name: "OA甲方管理员交流群",
    lastMessage: "AwayFu：@总有刁民想...",
    unread: 3,
    time: "09:30",
    avatar: "https://example.com/avatar1.png"
  },
  {
    id: 2,
    name: "汉兴经济技术理论群",
    lastMessage: "古青：周期，消费，这...",
    unread: 0,
    time: "09:15",
    avatar: "https://example.com/avatar2.png"
  },
  // 更多群组...
]);
  const selectGroup = (groupId) => {
    activeGroupId.value = groupId;
    // 这里可以触发加载群组详细消息
  };
  </script>
  
  <style scoped>
  .group-list {
    padding: 12px;
  }
  
  .group-item {
    display: flex;
    align-items: center;
    padding: 12px;
    cursor: pointer;
    border-radius: 8px;
    transition: background-color 0.2s;
  
    &:hover {
      background-color: #f5f5f5;
    }
  
    &.active {
      background-color: #e8f4ff;
    }
  }
  
  .group-info {
    flex: 1;
    margin-left: 12px;
    min-width: 0;
  
    .group-name {
      font-weight: 500;
      margin-bottom: 4px;
      overflow: hidden;
      text-overflow: ellipsis;
      white-space: nowrap;
    }
  
    .last-message {
      color: #666;
      font-size: 12px;
      overflow: hidden;
      text-overflow: ellipsis;
      white-space: nowrap;
    }
  }
  
  .right-info {
    display: flex;
    flex-direction: column;
    align-items: flex-end;
    margin-left: 12px;
  
    .time {
      font-size: 12px;
      color: #999;
      margin-bottom: 4px;
    }
  }
  
  .unread-badge {
    :deep(.el-badge__content) {
      background-color: #f56c6c;
    }
  }
  </style>