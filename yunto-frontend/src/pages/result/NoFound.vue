<template>
  <a-result status="404" title="404" sub-title="抱歉，您访问的页面不存在">
    <template #extra>
      <a-button type="primary" @click="backToHome">返回主页</a-button>
    </template>
  </a-result>
</template>

<script lang="ts" setup>
import router from '@/router'

const backToHome = () => {
  router.push('/')
}
</script>
<style scoped>
.desc p {
  margin-bottom: 1em;
}
</style>
