
import { defineStore } from 'pinia'
import type { User }  from '@/types/user'

/* 用户的仓库 */

export  const useUserStore = defineStore('user', {
  state: () => {
    return {
      tokenHead: '',
      token: '',
      userInfo: {} as User
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
    setUserInfo(userInfo: User) {
      this.userInfo = userInfo;
    },
    isAuthenticated(){
      return (this.token === '' && this.tokenHead === '') ? true : false;
    },
    resetAll() {
      this.tokenHead = '',
      this.token = '',
      this.userInfo = {} as User //用户信息
    }
  },
  persist: {
    storage: sessionStorage,
  },
});
