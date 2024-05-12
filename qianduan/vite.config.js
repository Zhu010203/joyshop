import { fileURLToPath, URL } from 'node:url'

import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [
    vue(),
  ],
  resolve: {
    alias: {
      '@': fileURLToPath(new URL('./src', import.meta.url))
    }
  },
  server:{//配置代理服务器
    proxy: {
      '/api': {
        target: 'http://10.37.32.52:81/',
        //target: 'http://10.37.236.120:81/',
        //target: 'http://10.104.124.159:81/',
        //target: 'http://10.104.38.161:81/',
        //代理服务器地址
        changeOrigin: true,                 //设置允许跨域
        rewrite: (path) => path.replace(/^\/api/, '')
      },
      '/photo': {
        target: 'http://10.37.32.52:81/',
        //target: 'http://10.37.236.120:81/',
        //target: 'http://10.104.124.159:81/',
        //target: 'http://10.104.38.161:81/',
      }
    },
    port: 4000
  },

})
