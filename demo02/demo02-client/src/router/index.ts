import { createRouter, createWebHistory } from 'vue-router'
import Home from '../components/Home.vue'
import Login from '../components/Login.vue'
import Register from '../components/Register.vue'
import Profile from '../components/Profile.vue'
import ConcertDetail from '../components/ConcertDetail.vue'
import Order from '../components/Order.vue'
import SeatSelect from '../components/SeatSelect.vue'
import OrderDetail from '../components/OrderDetail.vue'
import AdminDashboard from '../components/AdminDashboard.vue'
import Payment from '../components/Payment.vue'
// 添加反馈模块的导入
import UserFeedbackView from '../views/UserFeedbackView.vue'
import AdminFeedbackView from '../views/AdminFeedbackView.vue'

const routes = [
  { path: '/', name: 'Home', component: Home },
  { path: '/login', name: 'Login', component: Login },
  { path: '/register', name: 'Register', component: Register },
  { path: '/profile', name: 'Profile', component: Profile },
  { path: '/concert/:id', name: 'ConcertDetail', component: ConcertDetail },
  { path: '/order/:concertId', name: 'Order', component: Order },
  { path: '/seat/:concertId', name: 'SeatSelect', component: SeatSelect },
  { path: '/order-detail/:id', name: 'OrderDetail', component: OrderDetail },
  { path: '/admin', name: 'AdminDashboard', component: AdminDashboard },
  { path: '/payment', name: 'Payment', component: Payment },
  // 添加反馈模块的路由
  { path: '/feedback', name: 'UserFeedback', component: UserFeedbackView },
  { path: '/admin/feedback', name: 'AdminFeedback', component: AdminFeedbackView },
]

const router = createRouter({
  history: createWebHistory(),
  routes,
})

export default router 