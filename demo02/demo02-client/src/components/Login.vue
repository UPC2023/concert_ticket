<template>
  <NavButtons />
  <div class="login-bg">
    <div class="login-page">
      <el-card class="login-card">
        <h2>用户登录</h2>
        <el-form :model="form" ref="loginFormRef" label-width="80px">
          <el-form-item label="用户名">
            <el-input v-model="form.username" placeholder="请输入用户名" />
          </el-form-item>
          <el-form-item label="密码">
            <el-input v-model="form.password" type="password" placeholder="请输入密码" />
          </el-form-item>
          <el-form-item label="登录身份">
            <el-radio-group v-model="form.role">
              <el-radio label="USER">普通用户</el-radio>
              <el-radio label="ADMIN">管理员</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="onLogin">登录</el-button>
          </el-form-item>
        </el-form>
      </el-card>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import axios from 'axios'
import { ElMessage } from 'element-plus'
import { useRouter } from 'vue-router'
import NavButtons from './NavButtons.vue'

const form = ref({ username: '', password: '', role: 'USER' })
const loginFormRef = ref()
const router = useRouter()

const onLogin = async () => {
  try {
    const res = await axios.post('/api/user/login', form.value)
    if (res.data.success) {
      ElMessage.success('登录成功')
      // 保存用户信息到localStorage
      localStorage.setItem('user', JSON.stringify(res.data.user))
      if (res.data.user.role === 'ADMIN') {
        router.push('/admin')
      } else {
        router.push('/')
      }
    } else {
      ElMessage.error(res.data.message || '登录失败')
    }
  } catch (e) {
    ElMessage.error('请求失败')
  }
}
</script>

<style scoped>
.login-bg {
  min-height: 100vh;
  width: 100vw;
  background: url('https://pic.616pic.com/photoone/00/06/39/61975cab0e8f01513.jpg!/fw/1120') no-repeat center center;
  background-size: cover;
  display: flex;
  align-items: center;
  justify-content: center;
}
.login-page {
  min-height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
  background: rgba(245,245,245,0.7);
  width: 100vw;
}
.login-card {
  width: 360px;
  padding: 32px 24px 24px 24px;
  box-sizing: border-box;
}
</style> 