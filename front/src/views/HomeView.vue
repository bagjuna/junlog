<script setup lang="ts">
import {onMounted, reactive} from "vue";
import {container} from "tsyringe";
import PostRepository from "@/repository/PostRepository.ts";
import PostComponent from "@/components/PostComponent.vue";

const POST_REPOSITORY = container.resolve(PostRepository);


const state = reactive({
  postList: [],

})

function getList() {
  POST_REPOSITORY.getList().then(postList => {
    console.log('>>>',postList)
    state.postList = postList;
  })
}

onMounted(() => {
  getList()
})

</script>

<template>
  <div class="content">
    <ul class="posts">
      <li v-for="post in state.postList" :key="post.id">
        <PostComponent :post="post" />
      </li>
    </ul>
  </div>
</template>
<style scoped lang="scss">
ul {
  list-style: none;
  padding: 0;

  li {
    margin-bottom: 2rem;

    .title {
      a {
        font-size: 1.1rem;
        color: #383838;
        text-decoration: none;
      }

      &:hover {
        text-decoration: underline;
      }
    }

    .content {
      font-size: 0.85rem;
      margin-top: 8px;
      line-height: 1.4;
      color: #7e7e7e;
    }

    &:last-child {
      margin-bottom: 0;
    }

    .sub {
      margin-top: 8px;
      font-size: 0.78rem;

      .regDate {
        margin-left: 10px;
        color: #6b6b6b;
      }
    }
  }
}
</style>
