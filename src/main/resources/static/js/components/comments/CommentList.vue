<template theme="dark">
    <v-list lines="two" class="px-3">
      <v-list-subheader>Comments</v-list-subheader>

      <template
              v-for="(item, index) in comments"
              :key="index"
              :value="item"
      >
          <v-divider
                  v-if="index > 0"
                  :key="'item' + index"
          ></v-divider>
          <comment-item
                  :comment="item"
          ></comment-item>
      </template>


      <comment-form :post-id="postId"></comment-form>
    </v-list>
</template>

<script>
    import CommentForm from 'components/comments/CommentForm.vue'
    import CommentItem from 'components/comments/CommentItem.vue'
    export default {
        name: 'CommentList',
        components: {CommentForm, CommentItem},
        props: ['comments', 'postId']
    }
</script>

<style scoped>
</style>