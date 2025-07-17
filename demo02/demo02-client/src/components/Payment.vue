<template>
  <NavButtons />
  <div class="payment-layout">
    <!-- 左侧：订单信息 -->
    <el-card class="order-info-card">
      <div class="order-title">订单信息</div>
      <div class="concert-title">{{ concert.title }}</div>
      <div class="concert-info">
        <div>艺人：{{ concert.artist }}</div>
        <div>场馆：{{ concert.venue }}</div>
        <div>时间：{{ formatTime(concert.concertTime) }}</div>
      </div>
      <div class="seat-info-title">座位信息</div>
      <el-table :data="seatDetail" border size="small" class="seat-table">
        <el-table-column prop="type" label="区域" width="80" />
        <el-table-column prop="seatNo" label="座位" width="120" />
        <el-table-column prop="price" label="价格" width="80">
          <template #default="scope">￥{{ scope.row.price }}</template>
        </el-table-column>
      </el-table>
    </el-card>
    <!-- 右侧：支付信息 -->
    <el-card class="pay-info-card">
      <div class="pay-title">支付信息</div>
      <div class="pay-row">票数：<b>{{ ticketCount }}</b></div>
      <div class="pay-row">总价：<span class="total-fee">￥{{ totalFee }}</span></div>
      <div class="pay-row pay-method-row">
        <span>选择支付方式：</span>
        <el-radio-group v-model="paymentMethod">
          <el-radio label="ALIPAY">支付宝</el-radio>
          <el-radio label="WECHAT">微信支付</el-radio>
          <el-radio label="BANK">银行卡</el-radio>
        </el-radio-group>
      </div>
      <el-button type="primary" size="large" class="pay-btn" :loading="paying" @click="onPay">
        立即支付{{ totalFee ? ` ￥${totalFee}` : '' }}
      </el-button>
      <div class="pay-desc">
        <ul>
          <li>支付成功后将发送电子票至您的邮箱</li>
          <li>请确保联系人信息准确无误</li>
          <li>演出票一经售出，不支持退换</li>
          <li>如有疑问请联系客服</li>
        </ul>
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
const concertId = route.query.concertId
const seatIds = route.query.seatIds
const orderId = route.query.orderId
const seatDetail = ref<any[]>([])
const paymentMethod = ref('ALIPAY')
const paying = ref(false)
const concert = ref<any>({})
const order = ref<any>(null)
const totalFee = ref(0)
const ticketCount = ref(0)

const formatTime = (time: string) => time ? time.replace('T', ' ').slice(0, 16) : ''

onMounted(async () => {
  if (orderId) {
    // 通过订单ID加载订单详情
    try {
      const res = await axios.get(`/api/order/${orderId}`)
      if (res.data.success && res.data.order) {
        order.value = res.data.order
        concert.value = order.value.concertTitle ? { title: order.value.concertTitle } : {}
        seatDetail.value = order.value.seatInfo ? JSON.parse(order.value.seatInfo) : []
        if (!Array.isArray(seatDetail.value)) seatDetail.value = [seatDetail.value]
        totalFee.value = order.value.totalAmount || 0
        ticketCount.value = order.value.quantity || seatDetail.value.length
      }
    } catch {}
  } else {
    // 老逻辑：通过选座参数加载
    try {
      const res = await axios.get(`/api/concert/${concertId}`)
      if (res.data.success) {
        concert.value = res.data.concert || {}
      }
    } catch {}
    try {
      seatDetail.value = JSON.parse(decodeURIComponent(route.query.seatDetail as string))
    } catch (e) {
      seatDetail.value = []
    }
    totalFee.value = Number(route.query.totalFee) || 0
    ticketCount.value = seatDetail.value.length
  }
})

const onPay = async () => {
  if (paying.value) return
  paying.value = true
  const userId = JSON.parse(localStorage.getItem('user') || '{}').id
  if (!userId) {
    ElMessage.error('未获取到用户信息，请重新登录')
    paying.value = false
    return
  }
  try {
    if (orderId && order.value) {
      // 已有订单，直接支付
      const res = await axios.post(`/api/order/${orderId}/pay`, {
        paymentMethod: paymentMethod.value
      })
      if (res.data.success) {
        ElMessage.success('支付成功')
        router.push(`/order-detail/${orderId}`)
      } else {
        ElMessage.error(res.data.message || '支付失败')
      }
    } else {
      // 选座直接跳转支付页，需先创建订单
      const totalFeeNum = Number(totalFee.value)
      const res = await axios.post('/api/order/create', {
        userId,
        concertId,
        quantity: ticketCount.value,
        seats: seatIds,
        seatDetail: JSON.stringify(seatDetail.value),
        unitPrice: totalFeeNum
      })
      if (res.data.success && res.data.order) {
        ElMessage.success('订单已生成，请支付')
        router.replace({ path: '/payment', query: { orderId: res.data.order.id } })
      } else {
        ElMessage.error(res.data.message || '订单创建失败')
      }
    }
  } catch (e) {
    ElMessage.error('支付失败')
  } finally {
    paying.value = false
  }
}
</script>

<style scoped>
.payment-layout {
  display: flex;
  justify-content: center;
  align-items: flex-start;
  gap: 48px;
  max-width: 1200px;
  margin: 0 auto;
  padding: 40px 0;
}
.order-info-card {
  flex: 1 1 0;
  min-width: 420px;
  max-width: 520px;
  border-radius: 10px;
  box-shadow: 0 2px 8px rgba(0,0,0,0.08);
  padding: 32px 32px 24px 32px;
}
.order-title {
  font-size: 22px;
  font-weight: bold;
  margin-bottom: 18px;
}
.concert-title {
  font-size: 20px;
  font-weight: bold;
  margin-bottom: 8px;
}
.concert-info {
  color: #666;
  margin-bottom: 18px;
}
.seat-info-title {
  font-size: 16px;
  font-weight: bold;
  margin: 18px 0 8px 0;
}
.seat-table {
  margin-bottom: 12px;
}
.pay-info-card {
  flex: 0 0 340px;
  border-radius: 10px;
  box-shadow: 0 2px 8px rgba(0,0,0,0.08);
  padding: 32px 32px 24px 32px;
}
.pay-title {
  font-size: 20px;
  font-weight: bold;
  margin-bottom: 18px;
}
.pay-row {
  font-size: 16px;
  margin-bottom: 16px;
}
.pay-method-row {
  display: flex;
  align-items: center;
  gap: 12px;
}
.total-fee {
  color: #e4393c;
  font-weight: bold;
  font-size: 18px;
}
.pay-btn {
  width: 100%;
  font-size: 18px;
  margin-top: 16px;
}
.pay-desc {
  margin-top: 24px;
  color: #888;
  font-size: 13px;
}
.pay-desc ul {
  padding-left: 18px;
  margin: 0;
}
.pay-desc li {
  margin-bottom: 6px;
}
</style> 