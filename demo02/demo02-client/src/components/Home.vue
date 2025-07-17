<template>
  <el-container style="min-height: 100vh;">
    <el-header style="padding:0;">
      <!-- 顶栏 -->
      <header class="top-bar">
        <div class="left">
          <button 
            class="nav-btn" 
            :class="{ active: activeTab === 'home' }"
            @click="switchTab('home')"
          >
            首页
          </button>
          <button 
            class="nav-btn" 
            :class="{ active: activeTab === 'all-shows' }"
            @click="switchTab('all-shows')"
          >
            全部演出
          </button>
        </div>
        <div class="center">
          <input class="search-input" type="text" placeholder="搜索演出、艺人、场馆..." />
          <button class="search-btn">🔍</button>
        </div>
        <div class="right">
          <template v-if="user">
            <el-dropdown @command="handleDropdown">
              <span class="user-dropdown">
                <img :src="userAvatar" class="avatar" alt="头像" />
                <span class="username">{{ user.username || '用户' }}</span>
              </span>
              <template #dropdown>
                <el-dropdown-menu>
                  <el-dropdown-item command="profile">个人中心</el-dropdown-item>
                  <el-dropdown-item divided command="logout">退出登录</el-dropdown-item>
                </el-dropdown-menu>
              </template>
            </el-dropdown>
          </template>
          
          <template v-else>
            <button class="user-btn" @click="goLogin">登录</button>
            <button class="user-btn" @click="goRegister">注册</button>
          </template>
        </div>
      </header>
    </el-header>
    <el-main style="padding:0;">
      <!-- 首页内容 -->
      <div v-if="activeTab === 'home'">
        <!-- 轮播图 -->
        <div class="carousel-wrapper">
          <el-carousel :interval="4000" type="card" height="340px">
            <el-carousel-item v-for="(img, idx) in images" :key="idx">
              <img :src="img" class="carousel-img" />
            </el-carousel-item>
          </el-carousel>
        </div>

        <!-- 反馈入口按钮 -->
        <div style="margin: 24px 0; text-align: center;">
          <el-button type="primary" @click="$router.push('/feedback')">用户反馈</el-button>
          <el-button type="success" @click="$router.push('/admin/feedback')">反馈管理</el-button>
        </div>

        <!-- 演唱会信息区 -->
        <div class="concert-list-section">
          <h2 class="concert-list-title">演唱会</h2>
          <el-row :gutter="24">
            <el-col v-for="concert in concerts" :key="concert.id" :xs="24" :sm="12" :md="8" :lg="6">
              <el-card class="concert-card" shadow="hover" @click="goConcertDetail(concert.id)">
                <img :src="concert.posterUrl || defaultPoster" class="concert-poster" alt="海报" />
                <div class="concert-info">
                  <el-tooltip effect="dark" :content="concert.title" placement="top">
                    <div class="concert-title">{{ concert.title }}</div>
                  </el-tooltip>
                  <div class="concert-artist">艺人：{{ concert.artist }}</div>
                  <div class="concert-venue">场馆：{{ concert.venue }}</div>
                  <div class="concert-time">时间：{{ formatTime(concert.concertTime) }}</div>
                  <div class="concert-price">票价：￥{{ concert.price }}</div>
                </div>
              </el-card>
            </el-col>
          </el-row>
          <el-empty v-if="concerts.length === 0" description="暂无演唱会信息" />
        </div>
        <div class="announcement-section">
          <h2 class="announcement-title">公告</h2>
          <el-empty v-if="announcements.length === 0" description="暂无公告" />
          <el-timeline v-else>
            <el-timeline-item
              v-for="item in announcements"
              :key="item.id"
              :timestamp="item.publishTime ? item.publishTime.slice(0, 16) : ''"
              placement="top"
            >
              <div class="announcement-item-title">{{ item.title }}</div>
              <div class="announcement-item-content">{{ item.content }}</div>
            </el-timeline-item>
          </el-timeline>
        </div>
      </div>

      <!-- 全部演出内容 -->
      <div v-if="activeTab === 'all-shows'">
        <AllShows />
      </div>
    </el-main>
  </el-container>
</template>

<script setup lang="ts">
import { ref, computed, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessageBox, ElMessage } from 'element-plus'
import axios from 'axios'
import AllShows from './AllShows.vue'

const router = useRouter()
const goLogin = () => router.push('/login')
const goRegister = () => router.push('/register')

// Tab切换状态
const activeTab = ref('home')

// Tab切换函数
const switchTab = (tab: string) => {
  activeTab.value = tab
}

// 轮播图片示例
const images = ref([
  'https://tse4-mm.cn.bing.net/th/id/OIP-C.GwGsbMh712bnkye_4QUt8gHaFj?w=247&h=185&c=7&r=0&o=7&dpr=1.1&pid=1.7&rm=3',
  'https://tse2-mm.cn.bing.net/th/id/OIP-C.WXdeDw0OKIb-cnjGBpyScQHaE0?w=260&h=180&c=7&r=0&o=5&dpr=1.1&pid=1.7',
  'https://tse3-mm.cn.bing.net/th/id/OIP-C.6cKlX1OJocoyzz_fLiVZVAHaEY?w=314&h=186&c=7&r=0&o=7&dpr=1.1&pid=1.7&rm=3',
  'https://tse4-mm.cn.bing.net/th/id/OIP-C.hhrDwLAPTZbIPwzvrvy7WAHaEa?w=202&h=120&c=7&r=0&o=7&dpr=1.1&pid=1.7&rm=3'
])

const user = ref<any>(null)
const userStr = localStorage.getItem('user')
if (userStr) {
  user.value = JSON.parse(userStr)
}
// 默认头像
const userAvatar = computed(() => user.value && user.value.avatar ? user.value.avatar : 'https://img1.baidu.com/it/u=1815736402,3704489962&fm=253&fmt=auto&app=138&f=JPEG?w=80&h=80')

const handleDropdown = async (command: string) => {
  if (command === 'logout') {
    try {
      await ElMessageBox.confirm('确定要退出登录吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      })
      localStorage.removeItem('user')
      user.value = null
      ElMessage.success('已退出登录')
      // 可选：跳转到首页
      router.push('/')
    } catch {
      // 取消退出
    }
  } else if (command === 'profile') {
    router.push('/profile')
  }
}

// 演唱会数据
const concerts = ref<any[]>([])
const defaultPoster = 'https://img1.baidu.com/it/u=1815736402,3704489962&fm=253&fmt=auto&app=138&f=JPEG?w=300&h=400'

const formatTime = (time: string) => {
  if (!time) return ''
  // 兼容ISO格式
  return time.replace('T', ' ').slice(0, 16)
}

const goConcertDetail = (id: number|string) => {
  router.push(`/concert/${id}`)
}

const announcements = ref<any[]>([])

onMounted(async () => {
  try {
    const res = await axios.get('/api/concert/list')
    if (res.data.success) {
      concerts.value = res.data.concerts || []
    } else {
      ElMessage.error(res.data.message || '获取演唱会失败')
    }
  } catch (e) {
    ElMessage.error('获取演唱会失败')
  }
  // 获取公告
  try {
    const res = await axios.get('/api/announcement/list')
    console.log('公告接口返回：', res.data)
    if (res.data.success) {
      announcements.value = res.data.announcements || []
    }
  } catch (e) {}
})
</script>

<style scoped>
.home-page {
  background: #f5f5f5;
  min-height: 100vh;
  width: 100vw;
  margin: 0;
  padding: 0;
  overflow-x: hidden;
}
.top-bar {
  width: 100%;
  display: flex;
  justify-content: space-between;
  align-items: center;
  background: #fff;
  padding: 0 24px;
  height: 64px;
  box-shadow: 0 2px 8px rgba(0,0,0,0.05);
  position: sticky;
  top: 0;
  left: 0;
  z-index: 10;
  box-sizing: border-box;
}
.left, .center, .right {
  display: flex;
  align-items: center;
}
.left {
  flex: 1 1 auto;
}
.center {
  flex: 2 1 0;
  justify-content: center;
}
.right {
  flex: 1 1 auto;
  justify-content: flex-end;
}
.nav-btn {
  background: none;
  border: none;
  font-size: 20px;
  margin-right: 18px;
  cursor: pointer;
  color: #222;
  padding: 10px 18px;
  border-radius: 4px;
  transition: background 0.2s;
  white-space: nowrap;
}
.nav-btn.active, .nav-btn:hover {
  background: #f0f0f0;
}
.search-input {
  height: 36px;
  width: 220px;
  border: 1px solid #ddd;
  border-radius: 4px 0 0 4px;
  padding: 0 12px;
  outline: none;
  font-size: 16px;
  box-sizing: border-box;
}
.search-btn {
  height: 38px;
  border: 1px solid #ddd;
  border-left: none;
  background: #ff6700;
  color: #fff;
  border-radius: 0 4px 4px 0;
  padding: 0 16px;
  cursor: pointer;
  font-size: 20px;
}
.user-btn {
  background: none;
  border: 1px solid #ff6700;
  color: #ff6700;
  margin-left: 12px;
  padding: 8px 18px;
  border-radius: 4px;
  cursor: pointer;
  font-size: 18px;
  transition: background 0.2s, color 0.2s;
  white-space: nowrap;
}
.user-btn:hover {
  background: #ff6700;
  color: #fff;
}
.user-dropdown {
  display: flex;
  align-items: center;
  cursor: pointer;
}
.avatar {
  width: 36px;
  height: 36px;
  border-radius: 50%;
  margin-right: 8px;
  object-fit: cover;
  vertical-align: middle;
}
.username {
  font-size: 16px;
  color: #333;
  margin-right: 12px;
}
.carousel-wrapper {
  width: 100%;
  margin: 0;
  background: #fff;
  border-radius: 0;
  box-shadow: none;
  overflow: hidden;
  box-sizing: border-box;
}
.carousel-img {
  width: 100%;
  height: 340px;
  object-fit: cover;
  display: block;
}
.concert-list-section {
  max-width: 1200px;
  margin: 40px auto 0 auto;
  padding: 0 16px 40px 16px;
  background: #fff;
  border-radius: 10px;
  box-shadow: 0 2px 8px rgba(0,0,0,0.08);
}
.concert-list-title {
  font-size: 24px;
  font-weight: bold;
  margin: 0 0 24px 0;
  padding-top: 24px;
  color: #222;
}
.concert-card {
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
  overflow: hidden;
  justify-content: flex-start;
}
.concert-poster {
  width: 100%;
  height: 180px;
  object-fit: cover;
  border-radius: 12px 12px 0 0;
}
.concert-title {
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
.concert-info {
  font-size: 15px;
  color: #444;
  margin: 0 18px 0 18px;
  display: flex;
  flex-direction: column;
  gap: 4px;
  align-items: flex-start;
  flex: 1 1 auto;
  max-height: none;
  overflow: visible;
}
.concert-card::after {
  content: '';
  display: block;
  height: 18px;
}
.concert-artist, .concert-venue, .concert-time, .concert-price {
  font-size: 14px;
  color: #666;
  margin-bottom: 4px;
}
.announcement-section {
  max-width: 1200px;
  margin: 40px auto 0 auto;
  padding: 0 16px 40px 16px;
  background: #fff;
  border-radius: 10px;
  box-shadow: 0 2px 8px rgba(0,0,0,0.08);
}
.announcement-title {
  font-size: 22px;
  font-weight: bold;
  margin: 0 0 24px 0;
  color: #222;
}
.announcement-item-title {
  font-weight: 600;
  margin-bottom: 4px;
}
.announcement-item-content {
  color: #555;
}
@media (max-width: 900px) {
  .top-bar {
    flex-direction: column;
    height: auto;
    padding: 0 8px;
  }
  .center {
    width: 100%;
    margin: 8px 0;
  }
  .search-input {
    width: 100px;
  }
  .carousel-img {
    height: 180px;
  }
}
</style> 