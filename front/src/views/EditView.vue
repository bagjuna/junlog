<script setup lang="ts">
import {defineProps, ref} from "vue";

import axios from "axios"
import router from "@/router";


const post = ref({
  id: 0,
  title: "",
  content: "",

})

const props = defineProps({
  postId: {
    type: [Number, String],
    required: true,

  }
});

axios.get(`/api/posts/${props.postId}`).then((response) => {
  post.value = response.data;
});

const edit = () => {
  axios.patch(`/api/posts/${props.postId}`, post.value).then(() => {
    router.replace({name: "home"})

  });
};

</script>

<template>
  <div class="mt-5">

    <el-input v-model="post.title"/>
  </div>

  <div class="mt-2">

    <el-input v-model="post.content" type="textarea" rows="15"/>
  </div>

  <div class="mt-2">
    <div class="d-flex justify-content-end"
    >
      <el-button type="warning"
                 @click="edit()"
      >수정 완료
      </el-button>
    </div>

  </div>

</template>

<style lang="scss" scoped>


</style>
