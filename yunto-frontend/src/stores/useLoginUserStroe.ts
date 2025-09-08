import { defineStore } from 'pinia'
import { ref } from 'vue'
import { getLoginUserUsingGet } from '@/api/userController.ts'

/**
 * 存储用户登录信息的状态
 */
// 一个状态就存储在一类要共享的数据
export const useLoginUserStore = defineStore('loginUser', () => {
  // 定义初始值
  const loginUser = ref<any>({
    userName: '未登录',
  })

  // 获取用户信息
  async function fetchLoginUser() {
      // todo 等待后端完成用户登录接口
        const res = await getLoginUserUsingGet();
        if (res.data.code === 0 && res.data.data) {
            loginUser.value = res.data.data
        }
    // 测试用户登录
    // setTimeout(() => {
    //   loginUser.value = { userName: '测试用户', id: 1 }
    // }, 3000)
  }

  // 测试用户登录,3秒后自动登录

  // 定义更改用户状态
  function setLoginUser(newLoginUser: any) {
    loginUser.value = newLoginUser
  }

  return { loginUser, fetchLoginUser, setLoginUser }
})
