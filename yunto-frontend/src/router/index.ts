import { createRouter, createWebHistory } from 'vue-router'
import HomePage from '@/pages/HomePage.vue'
import UserLoginPage from '@/pages/user/UserLoginPage.vue'
import UserRegisterPage from '@/pages/user/UserRegisterPage.vue'
import UserManagePage from '@/pages/admin/UserManagePage.vue'
import ACCESS_ENUM from '@/access/accessEnum.ts'
import AddPicturePage from '@/pages/AddPicturePage.vue'
import PictureManagePage from '@/pages/admin/PictureManagePage.vue'
import PictureDetailPage from '@/pages/picture/PictureDetailPage.vue'
import AddPictureBatchPage from '@/pages/AddPictureBatchPage.vue'
import { useLoginUserStore } from '@/stores/useLoginUserStroe.ts'
import { h } from 'vue'
import { HomeOutlined } from '@ant-design/icons-vue'
import NoAuthPage from '@/pages/result/NoAuthPage.vue'
import NoFound from '@/pages/result/NoFound.vue'


const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: '主页',
      component: HomePage,
      meta: {
        icon: () => h(HomeOutlined),
      },
    },
    {
      path: '/add_picture',
      name: '创建图片',
      component: AddPicturePage,
    },
    {
      path: '/add_picture/batch',
      name: '批量创建图片',
      component: AddPictureBatchPage,
    },
    {
      path: '/picture/:id',
      name: '图片详情',
      component: PictureDetailPage,
      props: true,
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
      meta: {
        hideInMenu: true,
      },
    },
    {
      path: '/admin',
      children: [
        {
          path: 'userManage',
          name: '用户管理',
          component: UserManagePage,
        },
        {
          path: 'pictureManage',
          name: '图片管理',
          component: PictureManagePage,
        },
      ],
      meta: {
        access: ACCESS_ENUM.ADMIN,
      },
    },
    {
      path: '/noAuth',
      name: '无权限',
      component: NoAuthPage,
    },
    {
      path: '/notFound',
      name: '未找到',
      component: NoFound,
    },
  ],
})

router.beforeEach((to) => {
  // ✅ This will work because the router starts its navigation after
  // the router is installed and pinia will be installed too
  const loginUserStore = useLoginUserStore()

  if (to.meta.requiresAuth && !loginUserStore.fetchLoginUser) return '/user/login'
})

export default router
