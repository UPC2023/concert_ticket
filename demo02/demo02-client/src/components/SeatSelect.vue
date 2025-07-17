<template>
  <NavButtons />
  <div class="seat-maoyan-layout">
    <!-- 左侧：座位区 -->
    <div class="seat-area">
      <div class="seat-legend">
        <span class="legend-item"><span class="legend-box"><svg class="seat-icon" viewBox="0 0 32 32"><rect x="6" y="12" width="20" height="10" rx="4" fill="#ffe4e0" stroke="#e4393c" stroke-width="2"/><rect x="9" y="22" width="4" height="6" rx="2" fill="#e4393c"/><rect x="19" y="22" width="4" height="6" rx="2" fill="#e4393c"/></svg></span>VIP座</span>
        <span class="legend-item"><span class="legend-box"><svg class="seat-icon" viewBox="0 0 32 32"><rect x="6" y="12" width="20" height="10" rx="4" fill="#fff" stroke="#409eff" stroke-width="2"/><rect x="9" y="22" width="4" height="6" rx="2" fill="#409eff"/><rect x="19" y="22" width="4" height="6" rx="2" fill="#409eff"/></svg></span>普通座</span>
        <span class="legend-item"><span class="legend-box"><svg class="seat-icon" viewBox="0 0 32 32"><rect x="6" y="12" width="20" height="10" rx="4" fill="#f4f8e8" stroke="#67c23a" stroke-width="2"/><rect x="9" y="22" width="4" height="6" rx="2" fill="#67c23a"/><rect x="19" y="22" width="4" height="6" rx="2" fill="#67c23a"/></svg></span>经济座</span>
        <span class="legend-item"><span class="legend-box"><svg class="seat-icon" viewBox="0 0 32 32"><rect x="6" y="12" width="20" height="10" rx="4" fill="#bbb" stroke="#bbb" stroke-width="2"/><rect x="9" y="22" width="4" height="6" rx="2" fill="#bbb"/><rect x="19" y="22" width="4" height="6" rx="2" fill="#bbb"/></svg></span>已售</span>
        <span class="legend-item"><span class="legend-box"><svg class="seat-icon" viewBox="0 0 32 32"><rect x="6" y="12" width="20" height="10" rx="4" fill="#ffe4b3" stroke="#e6a23c" stroke-width="2"/><rect x="9" y="22" width="4" height="6" rx="2" fill="#e6a23c"/><rect x="19" y="22" width="4" height="6" rx="2" fill="#e6a23c"/></svg></span>锁定</span>
        <span class="legend-item"><span class="legend-box"><svg class="seat-icon" viewBox="0 0 32 32"><rect x="6" y="12" width="20" height="10" rx="4" fill="#67c23a" stroke="#67c23a" stroke-width="2"/><rect x="9" y="22" width="4" height="6" rx="2" fill="#67c23a"/><rect x="19" y="22" width="4" height="6" rx="2" fill="#67c23a"/><polyline points="11,18 15,22 21,14" fill="none" stroke="#fff" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/></svg></span>已选</span>
      </div>
      <div class="screen-label">演唱舞台</div>
      <div class="seat-map">
        <div v-for="(row, rowIdx) in rowList" :key="row" class="seat-row">
          <span class="row-label">{{ row }}</span>
          <span
            v-for="col in maxCol"
            :key="col"
            class="seat"
            @click="toggleSeat(seatGrid[row]?.[col])"
          >
            <svg v-if="seatGrid[row]?.[col]" class="seat-icon" viewBox="0 0 32 32">
              <!-- 优先判断已选、已售、锁定 -->
              <template v-if="seatClass(seatGrid[row][col]) === 'seat-selected'">
                <rect x="6" y="12" width="20" height="10" rx="4" :fill="seatTypeColor(seatTypeByRow(rowIdx), true)" :stroke="seatTypeColor(seatTypeByRow(rowIdx), true)" stroke-width="2"/>
                <rect x="9" y="22" width="4" height="6" rx="2" :fill="seatTypeColor(seatTypeByRow(rowIdx), true)"/>
                <rect x="19" y="22" width="4" height="6" rx="2" :fill="seatTypeColor(seatTypeByRow(rowIdx), true)"/>
                <polyline points="11,18 15,22 21,14" fill="none" stroke="#fff" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
              </template>
              <template v-else-if="seatClass(seatGrid[row][col]) === 'seat-sold'">
                <rect x="6" y="12" width="20" height="10" rx="4" fill="#bbb" stroke="#bbb" stroke-width="2"/>
                <rect x="9" y="22" width="4" height="6" rx="2" fill="#bbb"/>
                <rect x="19" y="22" width="4" height="6" rx="2" fill="#bbb"/>
              </template>
              <template v-else-if="seatClass(seatGrid[row][col]) === 'seat-locked'">
                <rect x="6" y="12" width="20" height="10" rx="4" fill="#ffe4b3" stroke="#e6a23c" stroke-width="2"/>
                <rect x="9" y="22" width="4" height="6" rx="2" fill="#e6a23c"/>
                <rect x="19" y="22" width="4" height="6" rx="2" fill="#e6a23c"/>
              </template>
              <!-- 否则按类型渲染 -->
              <template v-else-if="seatTypeByRow(rowIdx) === 'VIP'">
                <rect x="6" y="12" width="20" height="10" rx="4" fill="#ffe4e0" stroke="#e4393c" stroke-width="2"/>
                <rect x="9" y="22" width="4" height="6" rx="2" fill="#e4393c"/>
                <rect x="19" y="22" width="4" height="6" rx="2" fill="#e4393c"/>
              </template>
              <template v-else-if="seatTypeByRow(rowIdx) === '普通'">
                <rect x="6" y="12" width="20" height="10" rx="4" fill="#fff" stroke="#409eff" stroke-width="2"/>
                <rect x="9" y="22" width="4" height="6" rx="2" fill="#409eff"/>
                <rect x="19" y="22" width="4" height="6" rx="2" fill="#409eff"/>
              </template>
              <template v-else-if="seatTypeByRow(rowIdx) === '经济'">
                <rect x="6" y="12" width="20" height="10" rx="4" fill="#f4f8e8" stroke="#67c23a" stroke-width="2"/>
                <rect x="9" y="22" width="4" height="6" rx="2" fill="#67c23a"/>
                <rect x="19" y="22" width="4" height="6" rx="2" fill="#67c23a"/>
              </template>
            </svg>
          </span>
        </div>
      </div>
    </div>
    <!-- 右侧：信息卡片 -->
    <div class="info-card">
      <div class="concert-cover">
        <img :src="getCoverUrl(concert.posterUrl)" alt="封面" />
      </div>
      <div class="concert-title">{{ concert.title }}</div>
      <div class="concert-info">
        <div>类型：{{ concert.category }}</div>
        <div>场馆：{{ concert.venue }}</div>
        <div>时间：{{ formatTime(concert.concertTime) }}</div>
        <div>票价：VIP ￥{{ vipPrice }}，普通 ￥{{ normalPrice }}，经济 ￥{{ economyPrice }}</div>
      </div>
      <div class="selected-info">
        <div>座位：<span class="seat-list">{{ selectedSeatsDisplay }}</span></div>
        <div>总价：<span class="total-fee">￥{{ totalFee }}</span></div>
      </div>
      <el-button type="primary" size="large" :loading="buying" @click="buyTicket" class="buy-btn">确认选座</el-button>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted, computed } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import axios from 'axios'
import { ElMessage, ElMessageBox } from 'element-plus'
import NavButtons from './NavButtons.vue'

const route = useRoute()
const router = useRouter()
const concert = ref<any>({})
const seats = ref<any[]>([])
const selectedSeats = ref<number[]>([])
const seatGrid = ref<{ [row: string]: { [col: number]: any } }>({})
const rowList = ref<string[]>([])
const maxCol = ref(15)
const buying = ref(false)
const MAX_SELECT = 6
const defaultCover = '/default-cover.png' // 可自定义默认封面

const formatTime = (time: string) => time ? time.replace('T', ' ').slice(0, 16) : ''

onMounted(async () => {
  const id = route.params.concertId
  try {
    const res = await axios.get(`/api/concert/${id}`)
    if (res.data.success) {
      concert.value = res.data.concert || {}
      await loadSeats(concert.value.id)
    } else {
      ElMessage.error(res.data.message || '获取演唱会信息失败')
    }
  } catch (e) {
    ElMessage.error('获取演唱会信息失败')
  }
})

const loadSeats = async (concertId: number) => {
  try {
    const res = await axios.get(`/api/concert-seat/concert/${concertId}`)
    if (res.data) {
      seats.value = res.data
      // 计算所有行（字母）和最大列
      const rowSet = new Set<string>()
      let colMax = 1
      seatGrid.value = {}
      seats.value.forEach(s => {
        rowSet.add(s.rowNum)
        if (!seatGrid.value[s.rowNum]) seatGrid.value[s.rowNum] = {}
        seatGrid.value[s.rowNum][s.col] = s
        if (s.col > colMax) colMax = s.col
      })
      rowList.value = Array.from(rowSet).sort()
      maxCol.value = colMax
    }
  } catch (e) {
    ElMessage.error('获取座位信息失败')
  }
}

// 计算票价
const normalPrice = computed(() => Math.round(concert.value.price || 0))
const vipPrice = computed(() => Math.round((concert.value.price || 0) * 1.2))
const economyPrice = computed(() => Math.round((concert.value.price || 0) * 0.8))

// 根据行号判断座位类型
function seatTypeByRow(rowIdx: number) {
  const total = rowList.value.length
  if (rowIdx < Math.ceil(total * 0.2)) return 'VIP'
  if (rowIdx < Math.ceil(total * 0.7)) return '普通'
  return '经济'
}
// 座位类型对应颜色
function seatTypeColor(type: string, selected = false) {
  if (type === 'VIP') return selected ? '#e4393c' : '#ffe4e0'
  if (type === '普通') return selected ? '#409eff' : '#fff'
  if (type === '经济') return selected ? '#67c23a' : '#f4f8e8'
  return '#bbb'
}

const toggleSeat = (seat: any) => {
  if (!seat || seat.status !== '可售') return
  if (selectedSeats.value.includes(seat.seatId)) {
    selectedSeats.value = selectedSeats.value.filter(id => id !== seat.seatId)
  } else {
    if (selectedSeats.value.length >= MAX_SELECT) {
      ElMessage.warning(`每次最多可选${MAX_SELECT}个座位`)
      return
    }
    selectedSeats.value.push(seat.seatId)
  }
}

const seatClass = (seat: any) => {
  if (!seat) return ''
  if (selectedSeats.value.includes(seat.seatId)) return 'seat-selected'
  if (seat.status === '可售') return 'seat-available'
  if (seat.status === '已售') return 'seat-sold'
  if (seat.status === '锁定') return 'seat-locked'
  return ''
}

// 选中座位显示：座位号(类型)
const selectedSeatsDisplay = computed(() => {
  if (!selectedSeats.value.length) return '无'
  return seats.value.filter(s => selectedSeats.value.includes(s.seatId)).map(s => {
    const rowIdx = rowList.value.indexOf(s.rowNum)
    const type = seatTypeByRow(rowIdx)
    return `${s.seatNo}(${type})`
  }).join(', ')
})

// 计算总价
const totalFee = computed(() => {
  let total = 0
  seats.value.forEach(s => {
    if (selectedSeats.value.includes(s.seatId)) {
      const rowIdx = rowList.value.indexOf(s.rowNum)
      const type = seatTypeByRow(rowIdx)
      if (type === 'VIP') total += (concert.value.price || 0) * 1.2
      else if (type === '普通') total += (concert.value.price || 0)
      else if (type === '经济') total += (concert.value.price || 0) * 0.8
    }
  })
  return Math.round(total).toLocaleString()
})

const buyTicket = async () => {
  if (selectedSeats.value.length === 0) {
    ElMessage.warning('请先选择座位')
    return
  }
  if (buying.value) return
  buying.value = true
  const userId = JSON.parse(localStorage.getItem('user') || '{}').id
  if (!userId) {
    ElMessage.error('未获取到用户信息，请重新登录')
    buying.value = false
    return
  }
  // 1. 批量锁定座位
  try {
    for (const seatId of selectedSeats.value) {
      const lockRes = await axios.post('/api/concert-seat/lock', { concertId: concert.value.id, seatId })
      if (!lockRes.data.success) {
        ElMessage.error('座位锁定失败，请重试')
        await releaseAllSeats()
        buying.value = false
        return
      }
    }
    // 2. 创建订单
    const seatDetail = seats.value.filter(s => selectedSeats.value.includes(s.seatId)).map(s => ({
      seatId: s.seatId,
      seatNo: s.seatNo,
      rowNum: s.rowNum,
      col: s.col,
      type: seatTypeByRow(rowList.value.indexOf(s.rowNum)),
      price: (() => {
        const t = seatTypeByRow(rowList.value.indexOf(s.rowNum));
        if (t === 'VIP') return Math.round((concert.value.price || 0) * 1.2)
        if (t === '普通') return Math.round(concert.value.price || 0)
        if (t === '经济') return Math.round((concert.value.price || 0) * 0.8)
        return 0
      })()
    }))
    // 计算总价（纯数字）
    let total = 0
    seatDetail.forEach(s => { total += s.price })
    // 创建订单
    const res = await axios.post('/api/order/create', {
      userId,
      concertId: concert.value.id,
      quantity: seatDetail.length,
      seats: selectedSeats.value.join(','),
      seatDetail: JSON.stringify(seatDetail),
      unitPrice: total
    })
    if (res.data.success && res.data.order) {
      ElMessage.success('订单已生成，请支付')
      router.push({ path: '/payment', query: { orderId: res.data.order.id } })
    } else {
      ElMessage.error(res.data.message || '订单创建失败')
      await releaseAllSeats()
    }
  } catch (e) {
    ElMessage.error('购票失败')
    await releaseAllSeats()
  } finally {
    buying.value = false
  }
}

const releaseAllSeats = async () => {
  for (const seatId of selectedSeats.value) {
    await axios.post('/api/concert-seat/release', { concertId: concert.value.id, seatId })
  }
}

const getCoverUrl = (cover: string) => {
  if (cover) return cover
  return defaultCover
}
</script>

<style scoped>
.seat-maoyan-layout {
  display: flex;
  justify-content: center;
  align-items: flex-start;
  gap: 48px;
  max-width: 1400px;
  margin: 0 auto;
}
.seat-area {
  min-width: 700px;
  flex: 1 1 0;
}
.seat-legend {
  display: flex;
  align-items: center;
  margin-bottom: 18px;
  flex-wrap: wrap;
  gap: 18px;
}
.screen-label {
  text-align: center;
  font-size: 16px;
  color: #888;
  margin-bottom: 18px;
  letter-spacing: 2px;
}
.seat-map {
  display: inline-block;
  background: #f8f8f8;
  padding: 24px 32px;
  border-radius: 12px;
  box-shadow: 0 2px 8px #eee;
  margin-bottom: 32px;
}
.seat-row {
  display: flex;
  align-items: center;
  margin-bottom: 8px;
}
.row-label {
  width: 24px;
  font-weight: bold;
  color: #666;
  margin-right: 8px;
  text-align: right;
}
.seat {
  width: 32px;
  height: 32px;
  margin: 2px;
  border-radius: 8px;
  display: inline-flex;
  align-items: center;
  justify-content: center;
  font-size: 15px;
  box-sizing: border-box;
  border: none;
  padding: 0;
  user-select: none;
  background: none;
  transition: background 0.2s, color 0.2s;
}
.seat-icon {
  width: 28px;
  height: 28px;
  display: block;
}
.legend-box {
  width: 28px;
  height: 28px;
  display: inline-flex;
  align-items: center;
  justify-content: center;
  border: none;
  background: none;
  margin-right: 2px;
  padding: 0;
}
.seat-available {
  cursor: pointer;
}
.seat-sold, .seat-locked {
  cursor: not-allowed;
  opacity: 0.7;
}
.info-card {
  width: 340px;
  min-width: 320px;
  padding: 24px 24px 16px 24px;
  border-radius: 12px;
  box-shadow: 0 2px 8px #eee;
  background: #fff;
}
.concert-cover img {
  width: 100%;
  border-radius: 8px;
  margin-bottom: 12px;
  object-fit: cover;
  background: #f5f5f5;
  min-height: 180px;
}
.concert-title {
  font-size: 20px;
  font-weight: bold;
  margin-bottom: 8px;
}
.concert-info {
  color: #666;
  font-size: 14px;
  margin-bottom: 16px;
}
.selected-info {
  margin-bottom: 16px;
  font-size: 15px;
}
.seat-list {
  color: #f56c6c;
}
.total-fee {
  color: #e4393c;
  font-weight: bold;
}
.buy-btn {
  width: 100%;
  margin-top: 12px;
}
</style> 