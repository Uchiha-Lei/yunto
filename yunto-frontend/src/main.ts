import { createApp } from 'vue'
import { createPinia } from 'pinia'

import Antd from 'ant-design-vue'
import 'ant-design-vue/dist/reset.css'

import App from './App.vue'
import router from './router'
import "@/access";


const app = createApp(App)

app.use(createPinia())
app.use(router)
// 引入ant-design-vue组件
app.use(Antd)

app.mount('#app')
