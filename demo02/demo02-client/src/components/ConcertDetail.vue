<template>
  <NavButtons />
  <div class="concert-detail-page">
    <el-card class="concert-detail-card">
      <div class="detail-header">
        <img :src="concert.posterUrl || defaultPoster" class="detail-poster" alt="海报" />
        <div class="detail-info">
          <h2>{{ concert.title }}</h2>
          <div class="detail-artist">艺人：{{ concert.artist }}</div>
          <div class="detail-venue">场馆：{{ concert.venue }}</div>
          <div class="detail-time">时间：{{ formatTime(concert.concertTime) }}</div>
          <div class="detail-price">
            票价：
            <span>VIP ￥{{ Math.round(concert.price * 1.2) }}</span> /
            <span>普通 ￥{{ Math.round(concert.price) }}</span> /
            <span>经济 ￥{{ Math.round(concert.price * 0.8) }}</span>
          </div>
          <el-button type="primary" size="large" class="buy-btn" @click="goOrder">购票</el-button>
        </div>
      </div>
      <div class="detail-desc">
        <h3>演唱会介绍</h3>
        <div>{{ concert.description || '暂无介绍' }}</div>
      </div>
    </el-card>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import axios from 'axios'
import { ElMessage } from 'element-plus'
import NavButtons from './NavButtons.vue'

const route = useRoute()
const router = useRouter()
const concert = ref<any>({})
const defaultPoster = 'https://img1.baidu.com/it/u=1815736402,3704489962&fm=253&fmt=auto&app=138&f=JPEG?w=300&h=400'

const formatTime = (time: string) => {
  if (!time) return ''
  return time.replace('T', ' ').slice(0, 16)
}

const goOrder = () => {
  router.push(`/order/${concert.value.id}`)
}

onMounted(async () => {
  const id = route.params.id
  try {
    const res = await axios.get(`/api/concert/${id}`)
    if (res.data.success) {
      concert.value = res.data.concert || {}
    } else {
      ElMessage.error(res.data.message || '获取演唱会详情失败')
    }
  } catch (e) {
    ElMessage.error('获取演唱会详情失败')
  }
})
</script>

<style scoped>
.concert-detail-page {
  min-height: 100vh;
  background: #f5f5f5;
  padding: 40px 0;
}
.concert-detail-card {
  max-width: 900px;
  margin: 0 auto;
  border-radius: 10px;
  box-shadow: 0 2px 8px rgba(0,0,0,0.08);
  padding: 32px 32px 24px 32px;
}
.detail-header {
  display: flex;
  gap: 32px;
}
.detail-poster {
  width: 240px;
  height: 320px;
  object-fit: cover;
  border-radius: 8px;
  box-shadow: 0 2px 8px rgba(0,0,0,0.08);
}
.detail-info {
  flex: 1;
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
  gap: 12px;
}
.detail-info h2 {
  margin: 0 0 8px 0;
  font-size: 28px;
  font-weight: bold;
}
.detail-artist, .detail-venue, .detail-time, .detail-price {
  font-size: 16px;
  color: #555;
}
.buy-btn {
  margin-top: 24px;
  width: 120px;
}
.detail-desc {
  margin-top: 40px;
}
.detail-desc h3 {
  margin-bottom: 12px;
  font-size: 20px;
  font-weight: 600;
}
</style> 