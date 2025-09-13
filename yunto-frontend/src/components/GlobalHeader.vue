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
          v-model:selectedKeys="current"
          mode="horizontal"
          :items="menus"
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
import { computed, h, ref } from 'vue'
import { HomeOutlined, LogoutOutlined } from '@ant-design/icons-vue'
import { MenuProps, message } from 'ant-design-vue'
import { type RouteRecordRaw, useRouter } from 'vue-router'
import { useLoginUserStore } from '@/stores/useLoginUserStroe.ts'
import { userLogoutUsingPost } from '@/api/userController.ts'
import checkAccess from '@/access/checkAccess.ts'
import ACCESS_ENUM from '@/access/accessEnum.ts'

const loginUserStore = useLoginUserStore()

// 菜单列表
const originMenus = [
  {
    key: '/',
    icon: () => h(HomeOutlined),
    label: '主页',
    title: '主页',
  },
  {
    key: '/add_picture',
    label: '创建图片',
    title: '创建图片',
  },
  {
    key: '/admin/userManage',
    label: '用户管理',
    title: '用户管理',
    meta: {
      access: ACCESS_ENUM.ADMIN,
    },
  },
  {
    key: '/admin/pictureManage',
    label: '图片管理',
    title: '图片管理',
    meta: {
      access: ACCESS_ENUM.ADMIN,
    },
  },
]

loginUserStore.fetchLoginUser()
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

const menuToRouteItem = (menu: any): RouteRecordRaw => {
  // 获取所有路由
  const routes = router.getRoutes()
  // 根据菜单的key查找对应的路由
  const route = routes.find((route) => route.path === menu.key)
  // 如果找到对应路由则返回，否则返回一个默认的空路由对象
  return route || ({} as RouteRecordRaw)
}

// 过滤菜单项
const filterMenus = (menus = [] as MenuProps['items']) => {
  // 过滤条件是一个回调函数 (menu) => { ... }, 返回 true 表示保留该菜单项，返回 false 表示过滤掉该菜单项
  return menus?.filter((menu) => {
    // 通过menu的key值找到对应的路由字段
    const item = menuToRouteItem(menu)
    // 如果是菜单项中没有对应的路由，就说明该菜单是自定义的，予以保留，返回true
    if (!item.path) {
      return true
    }
    // 如果有hideInMenu标记为true，则隐藏
    if (item.meta?.hideInMenu) {
      return false
    }
    // 根据权限过滤菜单，有权限则返回true，会保留该菜单
    return checkAccess(loginUserStore.loginUser, item.meta?.access as string)
  })
}

// 展示在菜单的路由数组
const menus = computed(() => {
  return filterMenus(originMenus)
})

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
