<template>
  <a-result status="error" title="您暂时访问无权限">
    <template #extra>
      <a-button key="console" type="primary" @click="backToHome">返回主页</a-button>
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
