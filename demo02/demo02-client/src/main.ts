import { createApp } from 'vue'
import './style.css'
import App from './App.vue'

// 引入 Element Plus
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import router from './router/index'

const app = createApp(App)
app.use(ElementPlus)
app.use(router)
app.mount('#app')
