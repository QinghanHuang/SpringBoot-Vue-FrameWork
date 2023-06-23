<script setup>

import {Lock, User} from "@element-plus/icons-vue";
</script>

<template>
  <div style="width: 100vw;height: 100vh;overflow: hidden;display: flex">
    <div style="flex: 1">
      <el-image style="width: 100%;height: 100%" fit="cover"
                src="https://images.unsplash.com/photo-1614587431569-caafde7537d3?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=2070&q=80"/>
    </div>
    <div class="welcome-title">
      <div style="font-size: 30px;font-weight: bold">欢迎来到我们的学习平台</div>
      <div style="margin-top: 10px">在这里你可以学习如何使用Java，如何搭建网站，并且与Java之父密切交流。</div>
      <div style="margin-top: 5px">在这里你可以同性交友，因为都是男的，没有学Java的女生。</div>
    </div>
    <div style="width: 400px;background-color: white;z-index: 1">
      <router-view/>
    </div>
  </div>
</template>

<style scoped>
.welcome-title {
  position: absolute;
  bottom: 30px;
  left: 30px;
  color: white;
  text-shadow: 0 0 10px black;
}
</style>