<script setup>
import {EditPen, Lock, Message, User} from "@element-plus/icons-vue";
import router from "@/router";
import {reactive, ref} from "vue";
import {ElMessage} from "element-plus";
import {post} from "@/net/axios";


const form = reactive({
  username: '',
  password: '',
  password_repeat: '',
  email: '',
  code: ''
})

const validateUsername = (rule, value, callback) => {
  if (value === '') {
    callback(new Error("Username cannot be empty"))
  } else if (!/^[a-zA-Z0-9\u4e00-\u9fa5]+$/.test(value)) {
    callback(new Error("Username cannot contain special characters"))
  } else {
    callback()
  }
}
const validatePass = (rule, value, callback) => {
  if (value === '') {
    callback(new Error('Please input the password again'))
  } else if (value !== form.password) {
    callback(new Error("Two inputs don't match!"))
  } else {
    callback()
  }
}

const rules = {
  username: [
    {validator: validateUsername, trigger: ['blur', 'change']},
    {min: 5, max: 10, message: 'Username must be between 5 and 10 characters in length', trigger: ['blur', 'change']},
  ],
  password: [
    {required: true, message: 'Please input password', trigger: 'blur'},
    {min: 8, max: 20, message: 'Password must be between 8 and 20 characters in length', trigger: ['blur', 'change']},
  ],
  password_repeat: [
    {validator: validatePass, trigger: ['blur', 'change']},
  ],
  email: [
    {required: true, message: 'Please input email address', trigger: 'blur'},
    {type: 'email', message: 'Email address illegal', trigger: ['blur', 'change']}
  ],
  code: [
    {required: true, message: 'Please input verify code', trigger: 'blur'},
  ]
}
const isEmailValid = ref(false)
const formRef = ref()
const coldTime = ref(0)


const onValidate = (prop, isValid) => {
  if (prop === 'email')
    isEmailValid.value = isValid
}

const register = () => {
  formRef.value.validate((isValid) => {
    if (isValid) {
      post('/api/auth/register', {
        username: form.username,
        password: form.password,
        email: form.email,
        code: form.code
      }, (message) => {
        ElMessage.success(message)
        router.push("/")
      })
    } else {
      ElMessage.warning('Please fill in the registration form completely')
    }
  })
}

const validateEmail = () => {
  coldTime.value = 60
  post('/api/auth/valid-register-email', {
    email: form.email
  }, (message) => {
    ElMessage.success(message)
    setInterval(() => coldTime.value--, 1000)
  }, (message) => {
    ElMessage.warning(message)
    coldTime.value = 0
  })
}


</script>

<template>
  <div style="text-align: center;margin: 0 20px">
    <div style="margin-top: 100px">
      <div style="font-size: 25px;font-weight: bold">Sign Up</div>
      <div style="font-size: 14px;color: grey">Welcome,Please Input User Detail Below</div>
    </div>
    <div style="margin-top: 50px">
      <el-form
          :model="form"
          :rules="rules"
          @validate="onValidate"
          ref="formRef"
      >
        <el-form-item prop="username">
          <el-input v-model="form.username" maxlength="10" type="text" placeholder="Username">
            <template #prefix>
              <el-icon>
                <User/>
              </el-icon>
            </template>
          </el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input v-model="form.password" maxlength="20" type="password" placeholder="Password">
            <template #prefix>
              <el-icon>
                <Lock/>
              </el-icon>
            </template>
          </el-input>
        </el-form-item>
        <el-form-item prop="password_repeat">
          <el-input v-model="form.password_repeat" type="password" maxlength="20" placeholder="Confirm Password">
            <template #prefix>
              <el-icon>
                <Lock/>
              </el-icon>
            </template>
          </el-input>
        </el-form-item>
        <el-form-item prop="email">
          <el-input v-model="form.email" type="email" placeholder="Email">
            <template #prefix>
              <el-icon>
                <Message/>
              </el-icon>
            </template>
          </el-input>
        </el-form-item>
        <el-form-item prop="code">
          <el-row :gutter="10" style="width: 100%">
            <el-col :span="17">
              <el-input v-model="form.code" type="text" maxlength="6" placeholder="Input Verify Code">
                <template #prefix>
                  <el-icon>
                    <EditPen/>
                  </el-icon>
                </template>
              </el-input>
            </el-col>
            <el-col :span="7" style="text-align: right">
              <el-button @click="validateEmail" type="success" :disabled="!isEmailValid||coldTime>0">
                {{coldTime > 0 ? 'Wait ' + coldTime + ' S' : 'Get Code'}}

              </el-button>
            </el-col>
          </el-row>
        </el-form-item>
      </el-form>

    </div>
    <div style="margin-top: 80px">
      <el-button style="width: 270px" type="warning" @click="register" plain>Register</el-button>
    </div>
    <div style="margin-top: 20px">
      <span style="font-size: 14px;line-height: 15px;color: grey">Have a Account? </span>
      <el-link type="primary" style="translate: 0 -2px" @click="router.push('/')">Sign In</el-link>
    </div>
  </div>
</template>

<style scoped>

</style>