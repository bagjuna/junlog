
<template>

  <el-header class="header">
    <el-menu mode="horizontal" router>
      <el-menu-item index="/">Home</el-menu-item>
      <el-menu-item index="/write">글 작성</el-menu-item>
      <el-menu-item index="/login">로그인</el-menu-item>
    </el-menu>
  </el-header>

</template>

<script setup lang="ts"></script>

<style scoped>

.header {
  margin: 0;
  width: 100%;
  height: 60px;

}

</style>
