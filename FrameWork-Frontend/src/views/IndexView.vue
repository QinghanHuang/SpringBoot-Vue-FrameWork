<script setup>
import {get} from "@/net/axios";
import {ElMessage} from "element-plus";
import router from "@/router";

const logout=()=>{
  get('/api/auth/logout',(message)=>{
    ElMessage.success(message)
    router.push('/')
  })
}


</script>

<template>
  <div>
    Welcome to Index
  </div>
  <div>
    <el-button @click="logout()"  type="danger" plain>Sign Out</el-button>
  </div>
</template>

<style scoped>

</style>