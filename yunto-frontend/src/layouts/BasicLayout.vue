<template>
  <div id="basicLayout">
    <a-layout class="flex-layout">
      <a-layout-header class="header">
        <GlobalHeader />
      </a-layout-header>
      <a-layout-content class="content">
        <router-view />
      </a-layout-content>
      <a-layout-footer class="footer">
        <a href="#" target="_blank">智能云图库 by Uchiha-Lei</a>
      </a-layout-footer>
    </a-layout>
  </div>
</template>
<script setup lang="ts">
import GlobalHeader from '@/components/GlobalHeader.vue'
</script>
<style scoped>
#basicLayout {
  min-height: 100vh;
  display: flex;
  flex-direction: column;
}

#basicLayout .flex-layout {
  min-height: 100vh;
  display: flex;
  flex-direction: column;
}

#basicLayout .header {
  padding-inline: 20px;
  background: white;
  color: unset;
  position: sticky;
  top: 0;
  z-index: 100;
}

#basicLayout .content {
  padding: 20px;
  flex: 1;
  background: linear-gradient(to right, #efefef, #fff);
  overflow-y: auto;
}

#basicLayout .footer {
  background: #efefef;
  padding: 16px;
  text-align: center;
}
</style>
