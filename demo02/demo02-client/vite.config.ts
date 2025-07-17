import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'

// https://vite.dev/config/
export default defineConfig({
  plugins: [vue()],
  server: {
    proxy: {
      '/api': {
        target: 'http://localhost:8089', // Spring Boot后端端口已改为8089
        changeOrigin: true,
        rewrite: path => path.replace(/^\/api/, '/demo02/api')
      }
    }
  }
})
