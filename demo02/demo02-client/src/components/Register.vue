<template>
  <NavButtons />
  <div class="register-page">
    <el-card class="register-card">
      <h2>用户注册</h2>
      <el-form :model="form" ref="registerFormRef" label-width="80px">
        <el-form-item label="用户名">
          <el-input v-model="form.username" placeholder="请输入用户名" />
        </el-form-item>
        <el-form-item label="电话">
          <el-input v-model="form.phone" placeholder="请输入电话" />
        </el-form-item>
        <el-form-item label="邮箱">
          <el-input v-model="form.email" placeholder="请输入邮箱" />
        </el-form-item>
        <el-form-item label="密码">
          <el-input v-model="form.password" type="password" placeholder="请输入密码" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="onRegister">注册</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import axios from 'axios'
import { ElMessage } from 'element-plus'
import { useRouter } from 'vue-router'
import NavButtons from './NavButtons.vue'

const form = ref({ username: '', phone: '', email: '', password: '' })
const registerFormRef = ref()
const router = useRouter()

const onRegister = async () => {
  try {
    const res = await axios.post('/api/user/register', form.value)
    if (res.data.success) {
      ElMessage.success('注册成功')
      router.push('/login')
    } else {
      ElMessage.error(res.data.message || '注册失败')
    }
  } catch (e) {
    ElMessage.error('请求失败')
  }
}
</script>

<style scoped>
.register-page {
  min-height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
  background: #f5f5f5;
}
.register-card {
  width: 400px;
  padding: 32px 24px 24px 24px;
  box-sizing: border-box;
}
</style> 