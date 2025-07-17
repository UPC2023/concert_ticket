<template>
  <el-container style="min-height: 100vh;">
    <el-main style="padding:0;">
      <div class="all-shows">
        <!-- 横向标签条筛选 -->
        <div class="filter-bar-horizontal">
          <div class="filter-row">
            <span class="filter-label">类型：</span>
            <div class="filter-tags">
              <span :class="['filter-tag', {active: selectedCategory === ''}]" @click="selectCategory('')">全部</span>
              <span v-for="item in categoryList" :key="item" :class="['filter-tag', {active: selectedCategory === item}]" @click="selectCategory(item)">{{ item }}</span>
            </div>
          </div>
          <div class="filter-row">
            <span class="filter-label">状态：</span>
            <div class="filter-tags">
              <span :class="['filter-tag', {active: selectedStatus === ''}]" @click="selectStatus('')">全部</span>
              <span v-for="item in statusList" :key="item.value" :class="['filter-tag', {active: selectedStatus === item.value}]" @click="selectStatus(item.value)">{{ item.label }}</span>
            </div>
          </div>
          <div class="filter-row">
            <span class="filter-label">地区：</span>
            <div class="filter-tags">
              <span :class="['filter-tag', {active: selectedAddress === ''}]" @click="selectAddress('')">全部</span>
              <span v-for="item in addressList" :key="item" :class="['filter-tag', {active: selectedAddress === item}]" @click="selectAddress(item)">{{ item }}</span>
            </div>
          </div>
          <div class="filter-row">
            <span class="filter-label">年份：</span>
            <div class="filter-tags">
              <span :class="['filter-tag', {active: selectedYear === ''}]" @click="selectYear('')">全部</span>
              <span v-for="item in yearList" :key="item" :class="['filter-tag', {active: selectedYear === item}]" @click="selectYear(item)">{{ item }}</span>
            </div>
          </div>
          <div class="filter-row">
            <span class="filter-label">月份：</span>
            <div class="filter-tags">
              <span :class="['filter-tag', {active: selectedMonth === ''}]" @click="selectMonth('')">全部</span>
              <span v-for="item in monthList" :key="item" :class="['filter-tag', {active: selectedMonth === item}]" @click="selectMonth(item)">{{ item }}月</span>
            </div>
          </div>
          <div class="filter-row">
            <span class="filter-label">标题：</span>
            <el-input v-model="searchTitle" placeholder="搜索演唱会" size="small" style="width: 180px" clearable @keyup.enter="fetchConcerts" />
            <el-button size="small" type="primary" @click="fetchConcerts">搜索</el-button>
          </div>
        </div>
        <!-- 演出卡片网格 -->
        <el-row :gutter="24" class="show-list">
          <el-col v-for="show in concerts" :key="show.id" :xs="12" :sm="8" :md="6" :lg="4">
            <el-card class="show-card" shadow="hover" @click="goDetail(show.id)" style="cursor:pointer">
              <img :src="show.posterUrl || defaultPoster" class="show-poster" alt="海报" />
              <el-tooltip effect="dark" :content="show.title" placement="top">
                <div class="show-title">{{ show.title }}</div>
              </el-tooltip>
              <div class="show-info-detail">
                <div><b>艺人：</b>{{ show.artist }}</div>
                <div><b>场馆：</b>{{ show.venue }}</div>
                <div><b>时间：</b>{{ show.concertTime ? show.concertTime.replace('T', ' ').slice(0, 16) : '' }}</div>
                <div><b>类型：</b>{{ getCategoryLabel(show.category) }}</div>
                <div><b>状态：</b>{{ getStatusLabel(show.status) }}</div>
                <div><b>票价：</b>￥{{ show.price }}</div>
              </div>
            </el-card>
          </el-col>
        </el-row>
        <el-pagination
          v-if="total > pageSize"
          class="show-pagination"
          background
          layout="total, prev, pager, next, jumper"
          :total="total"
          :page-size="pageSize"
          :current-page="currentPage"
          @current-change="handlePageChange"
        />
      </div>
    </el-main>
  </el-container>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import axios from 'axios'
import { useRouter } from 'vue-router'
const concerts = ref<any[]>([])
const total = ref(0)
const currentPage = ref(1)
const pageSize = ref(12)
const categoryList = ref(['流行','摇滚','古典','动漫','民谣','爵士','电子','其他'])
const addressList = ref(['北京','上海','广州','深圳','成都','杭州','南京','武汉','西安','重庆','天津','苏州','青岛','大连','厦门','香港','台北'])
const nowYear = new Date().getFullYear()
const yearList = ref([
  ...Array.from({length: 10}, (_,i)=> (nowYear-i).toString()),
  '更早'
])
const monthList = ref(Array.from({length:12},(_,i)=>(i+1).toString()))
const statusList = ref([
  {label:'即将开始', value:'UPCOMING'},
  {label:'进行中', value:'ONGOING'},
  {label:'已结束', value:'FINISHED'},
  {label:'待定', value:'COMING'},
  {label:'已取消', value:'CANCELLED'}
])

const selectedCategory = ref('')
const selectedStatus = ref('')
const selectedAddress = ref('')
const selectedYear = ref('')
const selectedMonth = ref('')
const searchTitle = ref('')
const defaultPoster = 'https://img1.baidu.com/it/u=1815736402,3704489962&fm=253&fmt=auto&app=138&f=JPEG?w=300&h=400'

const selectCategory = (cat:string) => { selectedCategory.value = cat; fetchConcerts() }
const selectStatus = (s:string) => { selectedStatus.value = s; fetchConcerts() }
const selectAddress = (addr:string) => { selectedAddress.value = addr; fetchConcerts() }
const selectYear = (y:string) => { selectedYear.value = y; fetchConcerts() }
const selectMonth = (m:string) => { selectedMonth.value = m; fetchConcerts() }

const fetchConcerts = async () => {
  let yearParam = selectedYear.value
  if(yearParam === '更早') yearParam = 'before'
  const params:any = {
    currentPage: currentPage.value,
    size: pageSize.value,
    title: searchTitle.value,
    status: selectedStatus.value,
    category: selectedCategory.value,
    address: selectedAddress.value,
    year: yearParam,
    month: selectedMonth.value
  }
  // 去除空参数
  Object.keys(params).forEach(key => {
    if(params[key] === '' || params[key] === undefined) delete params[key]
  })
  try {
    const res = await axios.get('/api/concert/page', { params })
    if(res.data && res.data.success) {
      concerts.value = res.data.concerts || res.data.data || []
      total.value = res.data.total || 0
    }
  } catch (error) {
    console.error('获取演唱会列表失败:', error)
  }
}
const handlePageChange = (page:number) => {
  currentPage.value = page
  fetchConcerts()
}

// 类别标签转换函数
const getCategoryLabel = (category: string) => {
  // 数据库中已经是中文，直接返回
  return category || '未知'
}

// 状态标签转换函数
const getStatusLabel = (status: string) => {
  const statusMap: { [key: string]: string } = {
    'UPCOMING': '即将开始',
    'ONGOING': '进行中',
    'FINISHED': '已结束',
    'CANCELLED': '已取消',
    'COMING': '待定'
  }
  return statusMap[status] || status
}

const router = useRouter()
const goDetail = (id: number) => {
  router.push(`/concert/${id}`)
}

onMounted(() => {
  fetchConcerts()
})
</script>

<style scoped>
.all-shows {
  background: #fff;
  border-radius: 0;
  box-shadow: none;
  padding: 24px 0 0 0;
  margin-top: 0;
  width: 100vw;
}
.filter-bar-horizontal {
  background: #fff;
  border-radius: 8px;
  box-shadow: 0 2px 8px rgba(0,0,0,0.08);
  padding: 18px 24px 8px 24px;
  margin-bottom: 24px;
}
.filter-row {
  display: flex;
  align-items: center;
  margin-bottom: 10px;
  flex-wrap: wrap;
}
.filter-label {
  color: #888;
  font-size: 15px;
  min-width: 48px;
  margin-right: 8px;
}
.filter-tags {
  display: flex;
  flex-wrap: wrap;
  gap: 8px;
}
.filter-tag {
  display: inline-block;
  padding: 3px 16px;
  border-radius: 16px;
  background: #f5f5f5;
  color: #333;
  font-size: 15px;
  cursor: pointer;
  border: 1px solid transparent;
  transition: all 0.2s;
  user-select: none;
}
.filter-tag.active {
  background: #ff6700;
  color: #fff;
  border: 1px solid #ff6700;
}
.filter-bar {
  display: flex;
  flex-wrap: wrap;
  gap: 12px;
  margin-bottom: 24px;
  align-items: center;
}
.filter-group {
  display: flex;
  align-items: center;
  gap: 6px;
}
.filter-label {
  color: #888;
  font-size: 15px;
}
.show-list {
  margin-top: 12px;
  display: flex;
  flex-wrap: wrap;
  gap: 24px 18px;
  justify-content: flex-start;
}
.show-card {
  width: 280px;
  min-height: 440px;
  max-height: none;
  height: auto;
  margin-bottom: 32px;
  margin-right: 18px;
  border-radius: 12px;
  box-shadow: 0 2px 8px rgba(0,0,0,0.08);
  background: #fff;
  display: flex;
  flex-direction: column;
  align-items: center;
  overflow: visible;
  justify-content: flex-start;
}
.show-poster {
  width: 100%;
  height: 180px;
  object-fit: cover;
  border-radius: 12px 12px 0 0;
}
.show-title {
  font-size: 18px;
  font-weight: bold;
  margin: 16px 0 8px 0;
  text-align: center;
  max-width: 90%;
  overflow: hidden;
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
  text-overflow: ellipsis;
  white-space: normal;
}
.show-score {
  color: #faaf00;
  font-size: 15px;
  font-weight: bold;
  text-align: center;
  margin-bottom: 8px;
}
.show-info {
  display: flex;
  justify-content: space-between;
  font-size: 13px;
  color: #888;
  margin: 0 8px 8px 8px;
}
.show-info-detail {
  font-size: 15px;
  color: #444;
  margin: 0 18px 0 18px;
  display: flex;
  flex-direction: column;
  gap: 4px;
  align-items: flex-start;
  max-height: none;
  overflow: visible;
}
.show-card::after {
  content: '';
  display: block;
  height: 18px;
}
.show-pagination {
  margin-top: 24px;
  text-align: center;
}
</style> 