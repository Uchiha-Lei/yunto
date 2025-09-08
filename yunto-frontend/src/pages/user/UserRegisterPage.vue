<template>
  <div id="user-login-page">
    <h1 class="title">智能云图助手 - Yunto</h1>
    <p class="desc">企业级图库管理助手</p>
    <a-form :model="formState" name="basic" autocomplete="off" @finish="handleSubmit">
      <a-divider>用户注册</a-divider>
      <a-form-item
        name="userAccount"
        :rules="[
          { required: true, message: '请输入用户名!' },
          {
            min: 6,
            message: '用户名不能少于6位',
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

      <a-form-item
        name="checkPassword"
        :rules="[
          { required: true, message: '请再次输入密码!' },
          {
            min: 8,
            message: '密码不能少于4位',
          },
        ]"
      >
        <a-input-password v-model:value="formState.checkPassword" placeholder="请再次输入密码!" />
      </a-form-item>

      <a-form-item name="remember" class="tip">
        已有账号？
        <router-link to="/user/login">去登录</router-link>
      </a-form-item>

      <a-form-item>
        <a-button type="primary" html-type="submit" class="submit-btn">注册</a-button>
      </a-form-item>
    </a-form>
  </div>
</template>

<script setup lang="ts">
import { reactive } from 'vue'
import { userRegisterUsingPost } from '@/api/userController.ts'
import { message } from 'ant-design-vue'
import router from '@/router'

const formState = reactive<API.UserRegisterRequest>({
  userAccount: '',
  userPassword: '',
  checkPassword: '',
})

/**
 * 表单提交
 * @param values
 */
const handleSubmit = async (values: any) => {
  try {
    if (values.userPassword !== formState.userPassword) {
      return
    }
    const res = await userRegisterUsingPost(values)
    // 注册成功，跳转至登录页面
    if (res.data.code === 0 && res.data.data) {
      message.success('注册成功,跳转至登录页面')
      await router.push({ path: '/user/login', replace: true })
    } else {
      message.error('注册失败')
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
