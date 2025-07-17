<template>
  <NavButtons />
  <div class="order-page">
    <el-card class="order-card">
      <div class="order-header">
        <img :src="concert.posterUrl || defaultPoster" class="order-poster" alt="海报" />
        <div class="order-info">
          <h2>{{ concert.title }}</h2>
          <div>艺人：{{ concert.artist }}</div>
          <div>场馆：{{ concert.venue }}</div>
          <div>时间：{{ formatTime(concert.concertTime) }}</div>
        </div>
      </div>
      <div class="order-section">
        <h3>票档</h3>
        <el-radio-group v-model="selectedPrice">
          <el-radio-button v-for="item in priceOptions" :key="item.label" :label="item.value">
            {{ item.label }} ￥{{ item.value }}
          </el-radio-button>
        </el-radio-group>
      </div>
      <div class="order-section">
        <el-button type="primary" size="large" class="seat-btn" @click="goSeatSelect">选座购票</el-button>
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
const priceOptions = ref<{ label: string, value: number }[]>([])
const selectedPrice = ref<number | null>(null)

const formatTime = (time: string) => {
  if (!time) return ''
  return time.replace('T', ' ').slice(0, 16)
}

onMounted(async () => {
  const id = route.params.concertId
  try {
    const res = await axios.get(`/api/concert/${id}`)
    if (res.data.success) {
      concert.value = res.data.concert || {}
      if (concert.value.price) {
        priceOptions.value = [
          { label: 'VIP', value: Math.round(concert.value.price * 1.2) },
          { label: '普通', value: Math.round(concert.value.price) },
          { label: '经济', value: Math.round(concert.value.price * 0.8) }
        ]
        selectedPrice.value = priceOptions.value[0].value
      }
    } else {
      ElMessage.error(res.data.message || '获取演唱会信息失败')
    }
  } catch (e) {
    ElMessage.error('获取演唱会信息失败')
  }
})

const goSeatSelect = () => {
  router.push(`/seat/${concert.value.id}`)
}
</script>

<style scoped>
.order-page {
  min-height: 100vh;
  background: #f5f5f5;
  padding: 40px 0;
}
.order-card {
  max-width: 800px;
  margin: 0 auto;
  border-radius: 10px;
  box-shadow: 0 2px 8px rgba(0,0,0,0.08);
  padding: 32px 32px 24px 32px;
}
.order-header {
  display: flex;
  gap: 32px;
}
.order-poster {
  width: 180px;
  height: 240px;
  object-fit: cover;
  border-radius: 8px;
  box-shadow: 0 2px 8px rgba(0,0,0,0.08);
}
.order-info {
  flex: 1;
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
  gap: 10px;
}
.order-section {
  margin-top: 32px;
}
.seat-btn {
  width: 180px;
  font-size: 18px;
}
</style> 