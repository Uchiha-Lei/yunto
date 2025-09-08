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
            <a-dropdown>
              <a-space>
                <a-avatar :src="loginUserStore.loginUser.userAvatar" />
                {{ loginUserStore.loginUser.userName || '无名是' }}
              </a-space>
              <template #overlay>
                <a-menu>
                  <a-menu-item @click="doLogout">
                    <LogoutOutlined />
                    退出登录
                  </a-menu-item>
                </a-menu>
              </template>
            </a-dropdown>
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
import {  h, ref } from 'vue'
import { HomeOutlined, LogoutOutlined } from '@ant-design/icons-vue'
import { MenuProps, message } from 'ant-design-vue'
import { useRouter } from 'vue-router'
import { useLoginUserStore } from '@/stores/useLoginUserStroe.ts'
import { userLogoutUsingPost } from '@/api/userController.ts'

const loginUserStore = useLoginUserStore()
loginUserStore.fetchLoginUser()

// 菜单列表
const items = ref<MenuProps['items']>([
  {
    key: '/',
    icon: () => h(HomeOutlined),
    label: '主页',
    title: '主页',
  },
  {
    key: '/admin/userManage',
    label: '用户管理',
    title: '用户管理',
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

// // 过滤菜单项
// const items = menus.filter((menu) => {
//   // todo 需要自己实现 menu 到路由 item 的转化
//   const item = menuToRouteItem(menu);
//   if (item.meta?.hideInMenu) {
//     return false;
//   }
//   // 根据权限过滤菜单，有权限则返回 true，则保留该菜单
//   return checkAccess(loginUserStore.loginUser, item.meta?.access as string);
// });


// 退出登录
const doLogout = async () => {
  const res = await userLogoutUsingPost()
  if (res.data.code === 0) {
    loginUserStore.setLoginUser({
      userName: '未登录',
    })
    message.success('用户退出登录')
    await router.push({ path: '/user/login', replace: true })
  } else {
    message.error('退出登录失败' + res.data.message)
  }
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
