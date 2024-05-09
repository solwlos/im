
import { defineStore } from 'pinia'
import { ref } from 'vue'

/* 用户的仓库 */

export const useUserStore = defineStore('user', () => {
  const tokenHead = ref<string>()
  const token = ref<string>()
  const userInfo = ref<any>({})
  /* 存储token */
  function setToken(storage: string) {
    token.value = storage;
  }
  /* 存储 token 头 */
  function setTokenHead(head: string) {
    tokenHead.value = head;
  }
  function setUserInfo(userInfo: any) {
    userInfo.value = userInfo;
  }
  function isAuthenticated(){
    console.log("isAuthenticated ->" + token.value !== null)
    console.log("isAuthenticated ->" + tokenHead.value !== null)
    return (token.value !== null && tokenHead.value !== null) ? true : false;
  }
  function resetAll() {
    tokenHead.value = '',
    token.value = '',
    userInfo.value = {} //用户信息
  }

  return { tokenHead, token, userInfo , setToken,setTokenHead,setUserInfo,isAuthenticated,resetAll }
})
// export  const useUserStore = defineStore('user', {
//   state: () => {
//     return {
//       tokenHead: '',
//       token: '',
//       userInfo: {} //用户信息
//     };
//   },
  
//   actions: {
//     /* 存储token */
//     setToken(storage: string) {
//       this.token = storage;
//     },
//     /* 存储 token 头 */
//     setTokenHead(head: string) {
//       this.tokenHead = head;
//     },
//     setUserInfo(userInfo: {}) {
//       this.userInfo = userInfo;
//     },
//     isAuthenticated(){
//       return (this.token !== null && this.tokenHead !== null) ? true : false;
//     },
//     resetAll() {
//       this.tokenHead = '',
//       this.token = '',
//       this.userInfo = {} //用户信息
//     }
//   }
// });
