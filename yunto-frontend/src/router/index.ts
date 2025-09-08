import { createRouter, createWebHistory } from 'vue-router'
import HomePage from '@/pages/HomePage.vue'
import UserLoginPage from '@/pages/user/UserLoginPage.vue'
import UserRegisterPage from '@/pages/user/UserRegisterPage.vue'
import UserManagePage from '@/pages/admin/UserManagePage.vue'
import ACCESS_ENUM from '@/access/accessEnum.ts'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: '主页',
      component: HomePage,
    },
    {
      path: '/user',
      children: [
        {
          path: 'login',
          name: '用户登录',
          component: UserLoginPage,
        },
        {
          path: 'register',
          name: '用户注册',
          component: UserRegisterPage,
        },
      ],
    },
    {
      path: '/admin',
      children: [
        {
          path: 'userManage',
          name: 'adminUserManage',
          component: UserManagePage,
        },
      ],
      meta: {
        access: ACCESS_ENUM.ADMIN,
      },
    },
  ],
})

export default router
