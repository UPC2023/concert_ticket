<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import axios from 'axios'
import NavButtons from './NavButtons.vue'

const route = useRoute()
const order = ref<any>(null)
const loading = ref(true)
const errorMsg = ref('')

onMounted(async () => {
  const id = route.params.id
  try {
    const res = await axios.get(`/api/order/${id}`)
    console.log('订单详情接口返回：', res.data)
    if (res.data.success && res.data.order) {
      order.value = res.data.order
    } else {
      errorMsg.value = res.data.message || '未找到订单信息'
    }
  } catch (e) {
    errorMsg.value = '获取订单信息失败'
  } finally {
    loading.value = false
  }
})

// 新增：格式化座位信息
function formatSeatInfo(seatInfo: string) {
  if (!seatInfo) return ''
  try {
    const arr = JSON.parse(seatInfo)
    if (Array.isArray(arr)) {
      return arr.map((s: any) => `${s.seatNo}${s.type ? '(' + s.type + ')' : ''}`).join(', ')
    }
    return seatInfo
  } catch {
    return seatInfo
  }
}
</script>

<template>
  <NavButtons />
  <div class="order-detail-page">
    <h2>订单详情</h2>
    <div v-if="loading">加载中...</div>
    <div v-else-if="errorMsg">{{ errorMsg }}</div>
    <div v-else-if="order">
      <div>订单号：{{ order.orderNo }}</div>
      <div>用户ID：{{ order.userId }}</div>
      <div>演唱会ID：{{ order.concertId }}</div>
      <div>座位：{{ formatSeatInfo(order.seatInfo) }}</div>
      <div>数量：{{ order.quantity }}</div>
      <div>单价：￥{{ order.unitPrice }}</div>
      <div>总价：￥{{ order.totalAmount }}</div>
      <div>状态：{{ order.orderStatus }}</div>
      <div v-if="order.qrCode">
        <div style="margin-top:18px;font-weight:bold;">入场二维码：</div>
        <img :src="'data:image/png;base64,' + order.qrCode" alt="二维码" style="margin-top:8px;width:160px;height:160px;" />
      </div>
    </div>
  </div>
</template>

<style scoped>
.order-detail-page {
  max-width: 600px;
  margin: 40px auto;
  background: #fff;
  border-radius: 10px;
  box-shadow: 0 2px 8px rgba(0,0,0,0.08);
  padding: 32px;
}
.order-detail-page h2 {
  margin-bottom: 24px;
}
.order-detail-page div {
  margin-bottom: 12px;
  font-size: 16px;
}
</style> 