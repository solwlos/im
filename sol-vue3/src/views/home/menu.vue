<template>
  <div class="sidebar-container">
    <div class="logo-container">
      <el-avatar
        :size="60"
        :src="logoSrc"
        class="logo"
      />
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
    <div class="more-options-container">
      <el-button type="text">
        <el-icon :size="24">
          <More />
        </el-icon>
      </el-button>
    </div>
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
    icon: 'Hashtag',
    label: '群聊'
  },
  {
    icon: 'Lightning',
    label: '游戏'
  },
  {
    icon: 'Cloud',
    label: '云盘'
  },
  {
    icon: 'ArrowRight',
    label: '更多'
  },
  {
    icon: 'Grid',
    label: '应用'
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
  background-color: #f5f7fa;
  display: flex;
  flex-direction: column;
  align-items: center;

}

.logo-container {
/*  */
}

.icon-container {
  flex: 1;
  display: flex;
  flex-direction: column;
  justify-content: space-around;
}

.more-options-container {

}
</style>