import { useLoginUserStore } from '@/stores/useLoginUserStroe'
import { createPinia } from 'pinia'
import { createApp } from 'vue'
import App from './App.vue'
import Antd from 'ant-design-vue'
import 'ant-design-vue/dist/reset.css'

import router from './router'
import '@/access/index.ts'

const pinia = createPinia()
const app = createApp(App)

app.use(pinia)
app.use(router)
// 引入ant-design-vue组件
app.use(Antd)
// ✅ works because the pinia instance is now active
const loginUserStore = useLoginUserStore()

app.mount('#app')
