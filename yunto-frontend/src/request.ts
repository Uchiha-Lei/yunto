import axios from 'axios'
import { message } from 'ant-design-vue'

// 创建Axios实例
const myAxios = axios.create({
  baseURL: 'http://localhost:8123',
  timeout: 60000,
  withCredentials: true,
})

// 全局请求拦截器
axios.interceptors.request.use(
  function (config) {
    // 在发送请求之前做些什么
    return config
  },
  function (error) {
    // 对请求错误做些什么
    return Promise.reject(error)
  },
)

// 全局响应拦截器
axios.interceptors.response.use(
  function (response) {
    const { data } = response
    // 判断未登录
    if (response.status === 40100) {
      if (
        !response.request.responseURL.include('user/get/login') &&
        !window.location.pathname.includes('/user/login')
      ) {
        message.warning('请先登录')
        window.location.href = `user/login?redirect=${window.location.href}`
      }
    }
    return response
  },
  function (error) {
    // 超出 2xx 范围的状态码都会触发该函数。
    // 对响应错误做点什么
    return Promise.reject(error)
  },
)

export default myAxios
