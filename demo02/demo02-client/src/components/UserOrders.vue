<script setup lang="ts">
import { ref, onMounted, onUnmounted } from 'vue'
import { useRouter } from 'vue-router'
import axios from 'axios'

const router = useRouter()
const orders = ref<any[]>([])
const loading = ref(true)
const errorMsg = ref('')
const countdowns = ref<Record<number, string>>({})
let timer: any = null

const user = JSON.parse(localStorage.getItem('user') || '{}')

const statusMap: Record<string, string> = {
  PENDING: '未支付',
  CONFIRMED: '已确认',
  CANCELLED: '已取消',
  COMPLETED: '已完成',
  PAID: '已支付',
  FAILED: '支付失败',
  REFUNDED: '已退款',
  UNUSED: '未使用',
  USED: '已使用',
  EXPIRED: '已过期'
}

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

function updateCountdowns() {
  const now = new Date().getTime()
  orders.value.forEach(order => {
    if (order.paymentStatus === 'PENDING' && order.createTime) {
      const create = new Date(order.createTime.replace('T', ' ')).getTime()
      const left = 30 * 1000 - (now - create)
      if (left > 0) {
        const sec = Math.floor(left / 1000)
        countdowns.value[order.id] = `00:${sec.toString().padStart(2, '0')}`
      } else {
        countdowns.value[order.id] = '已超时'
      }
    } else {
      countdowns.value[order.id] = ''
    }
  })
}

function startTimer() {
  timer = setInterval(() => {
    updateCountdowns()
    // 有超时订单时自动刷新
    if (orders.value.some(o => o.paymentStatus === 'PENDING' && countdowns.value[o.id] === '已超时')) {
      fetchOrders()
    }
  }, 1000)
}

function stopTimer() {
  if (timer) clearInterval(timer)
}

async function fetchOrders() {
  loading.value = true
  try {
    const res = await axios.get(`/api/order/user/${user.id}`)
    if (res.data.success) {
      orders.value = res.data.orders
      updateCountdowns()
    } else {
      errorMsg.value = res.data.message || '获取订单失败'
    }
  } catch (e) {
    errorMsg.value = '获取订单失败'
  } finally {
    loading.value = false
  }
}

onMounted(async () => {
  await fetchOrders()
  startTimer()
})
onUnmounted(stopTimer)

const goDetail = (id: number) => {
  router.push(`/order-detail/${id}`)
}

const goPay = (id: number) => {
  router.push({ path: '/payment', query: { orderId: id } })
}
</script>

<template>
  <div class="user-orders-full">
    <h2>我的购票</h2>
    <div v-if="loading">加载中...</div>
    <div v-else-if="errorMsg">{{ errorMsg }}</div>
    <el-table v-else :data="orders" style="width: 100%">
      <el-table-column prop="concertTitle" label="演唱会名称" />
      <el-table-column prop="concertId" label="演唱会ID" />
      <el-table-column prop="seatInfo" label="座位">
        <template #default="scope">
          {{ formatSeatInfo(scope.row.seatInfo) }}
        </template>
      </el-table-column>
      <el-table-column prop="quantity" label="数量" />
      <el-table-column prop="totalAmount" label="总价" />
      <el-table-column label="剩余支付时间">
        <template #default="scope">
          <span v-if="scope.row.paymentStatus === 'PENDING'">{{ countdowns[scope.row.id] }}</span>
        </template>
      </el-table-column>
      <el-table-column prop="orderStatus" label="状态">
        <template #default="scope">
          {{ statusMap[scope.row.orderStatus] || scope.row.orderStatus }}
        </template>
      </el-table-column>
      <el-table-column prop="orderNo" label="订单号" />
      <el-table-column label="操作">
        <template #default="scope">
          <el-button v-if="scope.row.paymentStatus === 'PENDING'" type="success" size="small" @click="goPay(scope.row.id)">去支付</el-button>
          <el-button type="primary" size="small" @click="goDetail(scope.row.id)">详情</el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<style scoped>
.user-orders-full {
  width: 100vw;
  min-height: 100vh;
  margin: 0;
  padding: 0 0 40px 0;
  background: #fafbfc;
  box-sizing: border-box;
}
h2 {
  margin-bottom: 20px;
  margin-left: 24px;
}
</style> 