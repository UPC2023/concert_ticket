<template>
  <NavButtons />
  <div class="profile-page">
    <el-container class="profile-container">
      <!-- 左侧菜单栏 -->
      <el-aside width="220px" class="profile-aside">
        <el-menu :default-active="activeMenu" @select="handleMenuSelect">
          <el-menu-item index="userinfo">用户信息</el-menu-item>
          <el-menu-item index="orders">我的购票</el-menu-item>
        </el-menu>
      </el-aside>
      <!-- 右侧主内容区 -->
      <el-main class="profile-main">
        <div v-if="activeMenu === 'userinfo'">
          <h2>用户信息</h2>
          <el-descriptions :column="1" border>
            <el-descriptions-item label="用户名">{{ user.username }}</el-descriptions-item>
            <el-descriptions-item label="电话">{{ user.phone }}</el-descriptions-item>
            <el-descriptions-item label="邮箱">{{ user.email }}</el-descriptions-item>
            <el-descriptions-item label="角色">{{ user.role }}</el-descriptions-item>
            <el-descriptions-item label="状态">{{ user.status }}</el-descriptions-item>
          </el-descriptions>
        </div>
        <div v-else-if="activeMenu === 'orders'">
          <UserOrders />
        </div>
      </el-main>
    </el-container>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import NavButtons from './NavButtons.vue'
import UserOrders from './UserOrders.vue'
const router = useRouter()

const activeMenu = ref('userinfo')
const handleMenuSelect = (key: string) => {
  activeMenu.value = key
}

// 获取当前用户信息
const user = ref<any>({})
const userStr = localStorage.getItem('user')
if (userStr) {
  user.value = JSON.parse(userStr)
}
</script>

<style scoped>
.profile-page {
  min-height: 100vh;
  background: #f5f5f5;
  padding: 32px 0;
}
.profile-container {
  width: 1500px;
  margin: 0 auto;
  background: #fff;
  border-radius: 10px;
  box-shadow: 0 2px 8px rgba(0,0,0,0.08);
  overflow: hidden;
}
.profile-aside {
  background: #f8f8f8;
  min-height: 400px;
  border-right: 1px solid #eee;
}
.profile-main {
  padding: 32px 40px;
}
</style> 