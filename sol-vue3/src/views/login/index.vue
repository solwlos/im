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
// import axios from 'axios'
import api from '@/api'
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
// const instance = axios.create({
//   baseURL: '/api',
//   timeout: 1000
// })
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
  try {
    const response = await api.request({
      method: 'post',
      url: '/sysUser/login',
      data: {
        username: ruleForm.username,
        password: ruleForm.password
      }
    });
    // 成功处理
    user.resetAll();
    // 保存登录后的信息
    user.token = response.data.token;
    user.tokenHead = response.data.tokenHead;
    user.userInfo = response.data.userInfo;
    router.push('/home');
    ElMessage({ message: '登录成功', type: 'success' });

  } catch (error) {
    // 错误处理
    console.log(error);
  }
}

const resetForm = (formEl: FormInstance | undefined) => {
  if (!formEl) return
  formEl.resetFields()
}
</script>
