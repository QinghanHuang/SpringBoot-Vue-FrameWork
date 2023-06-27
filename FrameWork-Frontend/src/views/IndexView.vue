<script setup>
import {get} from "@/net/axios";
import {ElMessage} from "element-plus";
import router from "@/router";
import {useStore} from "@/stores/counter";

const  store=useStore()
const logout=()=>{
  get('/api/auth/logout',(message)=>{
    ElMessage.success(message)
    store.auth.user=null
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