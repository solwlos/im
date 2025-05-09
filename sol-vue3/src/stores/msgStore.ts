
import { defineStore } from 'pinia'
import { ref } from 'vue'
import type { Message} from '@/types/msg'

/* 用户的仓库 */

export  const msgStore = defineStore('msgStore', {
  state: () => {
    return {
      replylist: [], // 回话列表
      historymsg: [] as Message[]   // 历史消息

    };
  },
  
  // actions: {
  //   /* 存储token */
  //   setReplylist(replylist: {}) {
      
  //   },
  //   /* 存储 token 头 */
  //   setHistorymsg(historymsg: [] ) {
      
  //   },
  //   resetAll() {

  //   }
  // },
  persist: {
    storage: localStorage,
  },
});
