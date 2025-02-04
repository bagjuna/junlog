<script setup lang="ts">
import {reactive, ref} from "vue";

import PostWrite from "@/entity/post/PostWrite.ts";
import {container} from "tsyringe";
import PostRepository from "@/repository/PostRepository.ts";
import {ElMessage} from "element-plus";
import {useRouter} from "vue-router";


const state = reactive({
  postWrite: new PostWrite(),
})

const POST_REPOSITORY = container.resolve(PostRepository);

const router = useRouter();

function write() {
  POST_REPOSITORY.write(state.postWrite)
    .then(() => {
      ElMessage.success("글 등록이 완료 됐습니다.");
      router.replace("/")
    })
    .catch((e) => {
      ElMessage.error(e.getMessage());
    });
}
</script>

<template>
  <el-form label-position="top">
    <el-form-item label="제목">
      <el-input v-model="state.postWrite.title" placeholder="제목을 입력해주세요."/>
    </el-form-item>
    <div class="mt-2">
      <el-form-item label="내용">
        <el-input v-model="state.postWrite.content" type="textarea" rows="15"/>
      </el-form-item>
    </div>
    <el-form-item>
      <el-button
        type="primary"> 작성 완료
      </el-button>
    </el-form-item>
  </el-form>
</template>

<style lang="scss" scoped>


</style>
