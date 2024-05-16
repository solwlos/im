<!-- eslint-disable vue/multi-word-component-names -->
<template>
  <el-form
    ref="ruleFormRef"
    :model="ruleForm"
    status-icon
    :rules="rules"
    label-width="120px"
    class="demo-ruleForm"
  >
    <el-form-item label="账号" prop="username">
      <el-input v-model.number="ruleForm.username" />
    </el-form-item>

    <el-form-item label="密码" prop="password">
      <el-input v-model="ruleForm.password" type="password" autocomplete="off" />
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="submitForm(ruleFormRef)">登录</el-button>
      <el-button @click="resetForm(ruleFormRef)">重置</el-button>
    </el-form-item>
  </el-form>
</template>

<script lang="ts" setup>
import { reactive, ref } from 'vue'
import { type FormInstance, type FormRules } from 'element-plus'
import axios from 'axios'
import { useUserStore } from '@/stores/user'
import router from '@/router'
import { ElMessage } from 'element-plus'
// import { storeToRefs } from 'pinia';

const user = useUserStore()
// 必须引入、才能通过actions中的方法 双向绑定 tokenHead,token 的值
// const {tokenHead,token} = storeToRefs(user);

const ruleFormRef = ref<FormInstance>()

const checkUsername = (rule: any, value: any, callback: any) => {
  if (!value) {
    return callback(new Error('Please input the age'))
  }
}
const validatePass = (rule: any, value: any, callback: any) => {
  if (value === '') {
    callback(new Error('Please input the password'))
  }
}
const instance = axios.create({
  baseURL: '/api',
  timeout: 1000
})

// baseURL: '/api',
// timeout: 1000,
// responseType: 'json',
// withCredentials: true,
// headers: {'X-Custom-Header': 'foobar'}
const ruleForm = reactive({
  password: '',
  username: ''
})

const rules = reactive<FormRules<typeof ruleForm>>({
  password: [{ validator: validatePass, trigger: 'blur' }],
  username: [{ validator: checkUsername, trigger: 'blur' }]
})

const submitForm = async (formEl: FormInstance | undefined) => {
  if (!formEl) return

  await instance({
    method: 'get',
    url: '/sysUser/login',
    params: {
      username: ruleForm.username,
      password: ruleForm.password
    }
  })
    .then((res) => {
      user.resetAll()
      console.log('开始重置')

      user.token = res.data.token
      user.tokenHead = res.data.tokenHead
      router.push('/home')
      ElMessage({ message: '登录成功', type: 'success' })
    })
    .catch((res) => {
      console.log(res.response)
      ElMessage({ message: res.response.data, type: 'error' })
    })
}

const resetForm = (formEl: FormInstance | undefined) => {
  if (!formEl) return
  formEl.resetFields()
}
</script>
