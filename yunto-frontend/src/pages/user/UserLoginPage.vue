<template>
  <div id="user-login-page">
    <h1 class="title">智能云图助手 - Yunto</h1>
    <p class="desc">企业级图库管理助手</p>
    <a-form :model="formState" name="basic" autocomplete="off" @finish="handleSubmit">
      <a-divider>用户登录</a-divider>
      <a-form-item
        name="userAccount"
        :rules="[
          { required: true, message: '请输入用户名!' },
          {
            min: 4,
            message: '用户名不能少于4位',
          },
        ]"
      >
        <a-input v-model:value="formState.userAccount" placeholder="请输入用户名" />
      </a-form-item>

      <a-form-item
        name="userPassword"
        :rules="[
          { required: true, message: '请输入密码!' },
          {
            min: 8,
            message: '密码不能少于4位',
          },
        ]"
      >
        <a-input-password v-model:value="formState.userPassword" placeholder="请输入密码" />
      </a-form-item>

      <a-form-item name="remember" class="tip">
        没有账号？
        <router-link to="/user/register">去注册</router-link>
      </a-form-item>

      <a-form-item>
        <a-button type="primary" html-type="submit" class="submit-btn">登录</a-button>
      </a-form-item>
    </a-form>
  </div>
</template>

<script setup lang="ts">
import { reactive } from 'vue'
import { userLoginUsingPost } from '@/api/userController.ts'
import { useLoginUserStore } from '@/stores/useLoginUserStroe.ts'
import { message } from 'ant-design-vue'
import router from '@/router'

const loginUserStore = useLoginUserStore()

const formState = reactive<API.UserLoginRequest>({
  userAccount: '',
  userPassword: '',
})

/**
 * 表单提交
 * @param values
 */
const handleSubmit = async (values: any) => {
  try {
    const res = await userLoginUsingPost(values)
    // 登录成功，把登录态保存到浏览器
    if (res.data.code === 0 && res.data.data) {
      // console.log(res.data)
      await loginUserStore.fetchLoginUser()
      message.success('登录成功')
      await router.push({ path: '/', replace: true })
    } else {
      message.error('登录失败' + res.data.message)
    }
  } catch (error) {
    message.error(error.message)
  }
}
</script>

<style scoped>
#user-login-page {
  max-width: 360px;
  margin: 0 auto;
}

.title {
  text-align: center;
  font-size: 32px;
  margin-bottom: 16px;
}

.desc {
  color: #808080;
  text-align: center;
  font-size: 16px;
  margin-bottom: 16px;
}

.tip {
  text-align: right;
  font-size: 14px;
  color: grey;
}

.submit-btn {
  width: 100%;
}
</style>
