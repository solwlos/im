<template>
  <div class="more-options-menu">
    <!-- 触发按钮，可自定义图标 -->
    <el-button type="text" @click="showMenu = !showMenu">
      <el-icon :size="24">
        <More />
      </el-icon>
    </el-button>

    <!-- 弹出菜单 -->
    <el-popover
      v-model="showMenu"
      placement="bottom"
      trigger="click"
      :close-on-click-outside="true"
      class="options-popover"
    >
      <div class="menu-content">
        <div 
          v-for="(item, index) in menuList" 
          :key="index" 
          class="menu-item"
          @click="handleItemClick(item)"
        >
          <span>{{ item.label }}</span>
        </div>
      </div>
      <!-- 这里用空标签占位触发，也可直接把按钮放这里 -->
      <template #reference></template> 
    </el-popover>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { ElButton, ElIcon, ElPopover } from 'element-plus'
import { More } from '@element-plus/icons-vue'

// 菜单数据
const menuList = [
  { label: '超级调色盘' },
  { label: '导入历史消息' },
  { label: '聊天记录迁移与备份' },
  { label: '检查更新' },
  { label: '帮助' },
  { label: '锁定' },
  { label: '设置' },
  { label: '关于' },
  { label: '退出账号' }
]

const showMenu = ref(false)

// 菜单点击事件
const handleItemClick = (item: { label: string }) => {
  showMenu.value = false
  console.log(`点击了：${item.label}`)
  // 可根据需求扩展路由跳转、函数调用等逻辑
  switch (item.label) {
    case '退出账号':
      // 示例：调用退出逻辑
      break
    case '设置':
      // 跳转设置页面等
      break
    // 其他菜单项逻辑...
  }
}
</script>

<style scoped>
.more-options-menu {
  display: inline-block;
}

.options-popover {
  min-width: 160px;
}

.menu-content {
  padding: 8px 0;
  background-color: #fff;
  border: 1px solid #ebeef5;
  border-radius: 4px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
}

.menu-item {
  padding: 8px 16px;
  cursor: pointer;
  transition: background-color 0.2s;
}

.menu-item:hover {
  background-color: #f5f7fa;
}
</style>