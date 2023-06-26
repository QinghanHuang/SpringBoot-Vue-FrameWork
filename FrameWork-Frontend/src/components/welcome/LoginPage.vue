<script setup>

import {Lock, User} from "@element-plus/icons-vue";
import {reactive} from "vue";
import {ElMessage} from "element-plus";
import {post} from "@/net/axios";
import router from "@/router";

const form = reactive({
  username:'',
  password:'',
  remember:false
})

const login = () => {
  if (!form.username || !form.password) {
    ElMessage.warning("Please Input Username and Password ")
  }
  console.log(form.username)
  console.log(form.password)
  console.log(form.remember)
  post('/api/auth/login', {
    username: form.username,
    password: form.password,
    remember: form.remember
  },(message)=>{
    ElMessage.success(message)
    router.push('/index')
  })
}

</script>

<template>
  <div style="text-align: center;margin: 0 20px">
    <div style="margin-top: 150px">
      <div style="font-size: 25px;font-weight: bold">Sign In</div>
      <div style="font-size: 14px;color: grey">Please use a username or email to sign in</div>
    </div>
    <div style="margin-top: 50px">
      <el-input v-model="form.username" type="text" placeholder="Username/Email">
        <template #prefix>
          <el-icon>
            <User/>
          </el-icon>
        </template>
      </el-input>
      <el-input  v-model="form.password" type="password" style="margin-top: 10px" placeholder="Password">
        <template #prefix>
          <el-icon>
            <Lock/>
          </el-icon>
        </template>
      </el-input>
    </div>
    <el-row style="margin-top: 5px">
      <el-col :span="12" style="text-align: left">
        <el-checkbox  v-model="form.remember" label="Rememver Me"/>
      </el-col>
      <el-col :span="12" style="text-align: right">
        <el-link>Forgot Password?</el-link>
      </el-col>
    </el-row>
    <div style="margin-top: 40px">
      <el-button @click="login" style="width: 270px" type="success" plain>Sign In</el-button>
    </div>
    <el-divider>
      <span style="color: grey;font-size: 13px">No Account</span>
    </el-divider>
    <div>
      <el-button style="width: 270px" @click="router.push('/register')" type="warning" plain>Sign Up</el-button>
    </div>
  </div>

</template>

<style scoped>

</style>