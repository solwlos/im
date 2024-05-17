
import { defineStore } from 'pinia'
import { ref } from 'vue'

/* 用户的仓库 */

export  const useUserStore = defineStore('user', {
  state: () => {
    return {
      tokenHead: '',
      token: '',
      userInfo: {} //用户信息
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
      console.log("this.token -> " +this.token)
      console.log("this.tokenHead -> " +this.tokenHead)
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

// export const useUserStore = defineStore('user', () => {
//   const tokenHead = ref<string>()
//   const token = ref<string>()
//   const userInfo = ref<any>({})
//   /* 存储token */
//   function setToken(storage: string) {
//     token.value = storage;
//   }
//   /* 存储 token 头 */
//   function setTokenHead(head: string) {
//     tokenHead.value = head;
//   }
//   function setUserInfo(userInfo: any) {
//     userInfo.value = userInfo;
//   }
//   function isAuthenticated(){
//     console.log("token.value -> "+ token.value)
//     console.log("tokenHead.value -> "+ tokenHead.value)
//     return (token.value !== null && tokenHead.value !== null) ? true : false;
//   }
//   function resetAll() {
//     tokenHead.value = '',
//     token.value = '',
//     userInfo.value = {} //用户信息
//   }

//   return { tokenHead, token, userInfo}
// })
