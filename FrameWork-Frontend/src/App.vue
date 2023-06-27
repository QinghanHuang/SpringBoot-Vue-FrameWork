<script setup>
import router from "@/router";
import {useStore} from "@/stores/counter";
import {get} from "@/net/axios";


const store = useStore()

if(store.auth.user == null) {
  get('/api/user/me', (message) => {
    store.auth.user = message
    router.push('/index')
  }, () => {
    store.auth.user = null
  })
}

</script>


<template>
  <router-view/>
</template>

<style scoped>

</style>
