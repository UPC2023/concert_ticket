<script setup lang="ts">
import { useRouter } from 'vue-router'
const router = useRouter()
const goBack = () => router.back()
const goHome = () => router.push('/')
</script>

<template>
  <div class="nav-btns">
    <el-button @click="goBack" type="primary" plain>返回</el-button>
    <el-button @click="goHome" type="success" plain>首页</el-button>
  </div>
</template>

<style scoped>
.nav-btns {
  margin-bottom: 24px;
  display: flex;
  gap: 12px;
}
</style> 