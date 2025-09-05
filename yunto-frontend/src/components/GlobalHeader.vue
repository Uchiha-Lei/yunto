<template>
  <div id="global-header">
    <a-row :wrap="false">
      <!-- logo -->
      <a-col flex="200px">
        <router-link to="/">
          <div class="title-bar">
            <img class="logo" src="@/assets/logo.png" alt="logo" />
            <div class="title">智能云图助手</div>
          </div>
        </router-link>
      </a-col>
      <!-- 菜单 -->
      <a-col flex="auto">
        <a-menu
          class="global-header"
          v-model:selectedKeys="current"
          mode="horizontal"
          :items="items"
          @click="doMenuClick"
        />
      </a-col>
      <!-- 登录 -->
      <a-col flex="120px">
        <div class="user-login-status">
          <div v-if="loginUserStore.loginUser.id">
            {{ loginUserStore.loginUser.userName || '无名是' }}
          </div>
          <div v-else>
            <a-button type="primary" href="/user/login">登录</a-button>
          </div>
        </div>
      </a-col>
    </a-row>
  </div>
</template>
<script lang="ts" setup>
import { h, ref } from 'vue'
import { HomeOutlined } from '@ant-design/icons-vue'
import { MenuProps } from 'ant-design-vue'
import { useRouter } from 'vue-router'
import { useLoginUserStore } from '@/stores/useLoginUserStroe.ts'

const loginUserStore = useLoginUserStore()
loginUserStore.fetchLoginUser()

const items = ref<MenuProps['items']>([
  {
    key: '/',
    icon: () => h(HomeOutlined),
    label: '主页',
    title: '主页',
  },
  {
    key: '/about',
    label: '关于',
    title: '关于',
  },
  {
    key: 'others',
    label: h('a', { href: '#', target: '_blank' }, '其他'),
    title: '其他',
  },
])
const router = useRouter()
// 菜单高亮项
const current = ref<string[]>([])
// 监听路由变换，菜单高亮项
router.afterEach((to) => {
  current.value = [to.path]
})

// 切换菜单事件
const doMenuClick = ({ key }) => {
  router.push({
    path: key,
  })
}
</script>

<style scoped>
#global-header .title-bar {
  display: flex;
  align-items: center;
}

.title {
  color: #000;
  font-size: 18px;
  margin-left: 16px;
}

.logo {
  height: 48px;
}
</style>
