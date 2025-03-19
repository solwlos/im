
import { defineStore } from 'pinia'
import { ref } from 'vue'
import User from '@/types/User'

/* 用户的仓库 */

export  const useUserStore = defineStore('user', {
  state: () => {
    return {
      tokenHead: '',
      token: '',
      userInfo: User
    };
  },
  
  actions: {
    /* 存储token */
    setToken(storage: string) {
      this.token = storage;
    },
    /* 存储 token 头 */
    setTokenHead(head: string) {
      this.tokenHead = head;
    },
    setUserInfo(userInfo: {}) {
      this.userInfo = userInfo;
    },
    isAuthenticated(){
      return (this.token === '' && this.tokenHead === '') ? true : false;
    },
    resetAll() {
      this.tokenHead = '',
      this.token = '',
      this.userInfo = {} //用户信息
    }
  },
  persist: {
    storage: sessionStorage,
  },
});
