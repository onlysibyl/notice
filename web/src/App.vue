<template>
  <div id="app" class="common-layout">
    <el-container>
      <div v-if="!$route.meta.login" style="text-align: right">
        <el-button @click="logout">退出登录</el-button>
      </div>
      <h1>校园通知管理平台</h1>
      <div v-if="!$route.meta.login">
        <el-menu
          :default-active="activeIndex"
          class="el-menu-demo"
          mode="horizontal"
          @select="handleSelect"
        >
          <el-sub-menu index="member">
            <template #title>成员管理</template>
            <el-menu-item index="user">用户查询</el-menu-item>
            <el-menu-item index="role">角色查询</el-menu-item>
          </el-sub-menu>
          <el-menu-item index="notice">通知一览</el-menu-item>
          <el-menu-item index="persMsg">个人消息</el-menu-item>
        </el-menu>
      </div>
      <el-main><router-view /></el-main>
      <el-footer>作者：覃佳玲</el-footer>
    </el-container>
  </div>
</template>

<script lang="ts" setup>
import router from "./router/index.js";
import { ref } from "vue";

const logout = () => {
  localStorage.clear();
  window.sessionStorage.clear();
  router.push("/login");
};

const activeIndex = ref("1");
const handleSelect = (key: string, keyPath: string[]) => {
  switch (key) {
    case "user":
      router.push({name:'User'}).then(() => {
        console.log('跳转到用户查询页面');
      }).catch((error) => {
        console.error('跳转失败:', error);
      });
      break;     
    case "role":
      router.push({name:'Role'}).then(() => {
        console.log('跳转到角色查询页面');
      }).catch((error) => {
        console.error('跳转失败:', error);
      });
      break;
    case "notice":
      router.push({name:'Notice'}).then(() => {
        console.log('跳转到通知一览页面');
      }).catch((error) => {
        console.error('跳转失败:', error);
      });
      break;
    case "persMsg":
      router.push({name:'Msg'}).then(() => {
        console.log('跳转到个人消息页面');
      }).catch((error) => {
        console.error('跳转失败:', error);
      });
      break;
  }
  console.log(key);
};
</script>


<!--

<script setup>
// This starter template is using Vue 3 <script setup> SFCs
// Check out https://v3.vuejs.org/api/sfc-script-setup.html#sfc-script-setup
import HelloWorld from './components/HelloWorld.vue'
</script>

<template>
  <img alt="Vue logo" src="./assets/logo.png" />
  <HelloWorld msg="Hello Vue 3 + Vite" />
</template>

-->

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
</style>
