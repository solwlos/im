<template>
  <div class="container">
      搜索框
      <el-input
          v-model="searchQuery"
          placeholder="搜索群组"
          clearable
         
          class="search-input"
      /> 
      <!-- @input="filterGroups" -->
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
  </div>
</template>

<script setup lang="ts">
import { ref, computed ,onMounted, onUnmounted  } from 'vue';
import type { Reply } from '@/types/msg'
import api from '@/api'
const activeGroupId = ref<string>();
const groupList = ref<Reply[]>([
  {
      id: "3",
      name: "OA甲方管理员交流群",
      lastMessage: "AwayFu：@总有刁民想...",
      type: 0,
      unread: 3,
      time: "09:30",
      avatar: "https://example.com/avatar1.png"
  },
  {
      id: "2",
      name: "汉兴经济技术理论群",
      lastMessage: "古青：周期，消费，这...",
      type: 0,
      unread: 0,
      time: "09:15",
      avatar: "https://example.com/avatar2.png"
  },
  {
      id: "0",
      name: "sol",
      lastMessage: "sol: test",
      type: 1,
      unread: 0,
      time: "09:15",
      avatar: "https://example.com/avatar2.png"
  },
  {
      id: "1",
      name: "123",
      lastMessage: "123: 开始测试",
      type: 1,
      unread: 0,
      time: "09:15",
      avatar: "https://example.com/avatar2.png"
  }
]);
const searchQuery = ref('');

const filteredGroupList = computed(() => {
  return groupList.value.filter((group: any)  =>
      group.name.toLowerCase().includes(searchQuery.value.toLowerCase())
  );
});

const selectGroup = (groupId: string) => {
  activeGroupId.value = groupId;
  // 这里可以触发加载群组详细消息
  console.log("groupId：", groupId);
};
onMounted(async () => {
  
  const response =await api.request({
    method: 'post',
    url: '/sysUser/searchQuery',
    data: {
    }
  });
  console.log('response.data.records:', response.data);
  // 确保子组件接收到正确的数据
  // groupList.value = JSON.parse(response.data);
});
</script>

<style scoped>
.container {
  padding-left: 20px;
  padding-top: 20px;
}

.group-list {
  padding: 0;
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

.search-input {
  width: 100%;
  padding: 12px;
  border-radius: 8px;
  margin-bottom: 12px;
  border: 1px solid #e4e7ed;
  transition: border-color 0.2s;
  box-sizing: border-box;

  &:hover {
      border-color: #c0c4cc;
  }

  &:focus-within {
      border-color: #409eff;
  }
}
</style>    