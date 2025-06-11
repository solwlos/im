<template>
  <div class="sidebar-container">
    <div class="logo-container">
      <el-button type="text" class="logo-button">
        <el-avatar
          :size="40"
          :src="logoSrc"
          class="logo"
        />
      </el-button>
    </div>
    <div class="icon-container">
      <el-button
        v-for="(icon, index) in icons"
        :key="index"
        type="text"
        @click="handleIconClick(icon)"
      >
        <el-icon :size="24">
          <component :is="icon.icon"></component>
        </el-icon>
      </el-button>
    </div>
    <!-- <div class="more-options-container">
      <el-button type="text">
        <el-icon :size="24">
          <More />
        </el-icon>
      </el-button>
    </div> -->
  </div>
</template>

<script setup>
import { ref } from 'vue';
import { ElAvatar, ElButton, ElIcon } from 'element-plus';
import { More } from '@element-plus/icons-vue';

// 侧边栏图标配置
const icons = [
  {
    icon: 'ChatDotRound',
    label: '聊天'
  },
  {
    icon: 'User',
    label: '联系人'
  },
  {
    icon: 'Star',
    label: '收藏'
  },
  {
    icon: 'ArrowRight',
    label: '更多'
  },
  {
    icon: 'Grid',
    label: '应用'
  },
  {
    icon: 'Setting',
    label: '设置'
  }
];

const logoSrc = 'https://your-logo-url-here'; // 替换为实际的 logo 图片地址

// 图标点击处理函数
const handleIconClick = (icon) => {
  console.log(`点击了 ${icon.label}`);
  // 这里可以添加具体的业务逻辑，比如跳转路由等
};
</script>

<style scoped>
.sidebar-container {
  width: 60px;
  height: 100vh;
  display: flex;
  flex-direction: column;
  align-items: center;
  padding-top: 5px; /* 减小顶部内边距 */
  padding-bottom: 5px; /* 减小底部内边距 */
}

.logo-container {
  margin-bottom: 5px; /* 减小logo与图标间的间距 */
  margin-top: 30px;
}

.logo-button {
  width: 100%;
  height: 50px; /* 减小logo按钮高度 */
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 0;
}

.icon-container {
  flex: 1;
  display: flex;
  flex-direction: column;
  /* justify-content: space-between; 替换为space-between减少间距 */
  width: 100%;
  
}

.more-options-container {
  margin-top: 5px; /* 减小更多选项的顶部间距 */
}

.el-button {
  width: 100%;
  height: 45px; /* 减小按钮高度 */
  display: flex; 
  justify-content: center;
  align-items: center; 
  padding-bottom: 50px;
  margin: 0;
}
</style>