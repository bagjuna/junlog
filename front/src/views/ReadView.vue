<script setup lang="ts">
import {defineProps, onMounted, reactive, ref} from "vue"
import axios from "axios";
import {useRouter} from "vue-router";
import {container} from "tsyringe";
import PostRepository from "@/repository/PostRepository.ts";
import type Post from "@/entity/post/Post.ts";


const props = defineProps<{
  postId: number,
}>();

const POST_REPOSITORY = container.resolve(PostRepository);

type StateType = {
  post: Post | null
}

const state = reactive<StateType>({
  post: null,
})

function getPost() {
  POST_REPOSITORY.get(props.postId)
    .then((post: Post) => {
      state.post = post
    })
    .catch((e) => {
      console.log(e)
    });
}

onMounted(() => {
    getPost()
  }
)

</script>

<template>
  <div v-if="state.post !== null">

    <el-row>
      <el-col :span="22" :offset="1">
        <div class="title">
          {{ state.post.title }}
        </div>
      </el-col>
    </el-row>

    <el-row :span="10" :offset="1">
      <el-col>
        <div class="content">
          {{ state.post.content }}
        </div>
      </el-col>
    </el-row>

    <el-row :span="10" :offset="1">
      <el-col>
        <div class="sub">
          <span class="regDate">{{ state.post.getDisplayRegDate() }}</span>
        </div>
      </el-col>
    </el-row>

  </div>
</template>

<style scoped lang="scss">
.title {
  font-size: 1.8rem;
  font-weight: 400;
  text-align: center;
}

.regDate {
  margin-top: 0.5rem;
  font-size: 0.78rem;
  font-weight: 300;
}

.content {
  margin-top: 1.88rem;
  font-weight: 300;

  word-break: break-all;
  white-space: break-spaces;
  line-height: 1.4;
  min-height: 5rem;
}

hr {
  border-color: #f9f9f9;
  margin: 1.2rem 0;
}

.footer {
  margin-top: 1rem;
  display: flex;
  font-size: 0.78rem;
  justify-content: flex-end;
  gap: 0.8rem;

  .delete {
    color: red;
  }
}

.comments {
  margin-top: 4.8rem;
}
</style>
