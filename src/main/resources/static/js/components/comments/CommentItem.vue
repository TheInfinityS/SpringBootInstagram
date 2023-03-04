<template>
  <v-list-item>
      <v-row align="start">
        <v-col>
        <v-list-item-avatar>
            <v-avatar v-if="comment.author&&comment.author.imageUrl"
                    size="35px">
              <v-img
                :src="comment.author.imageUrl"
                :alt="comment.author.username"
              ></v-img>
            </v-avatar>

            <v-avatar v-else
                    size="36px"
                    color="indigo">
              <v-icon icon="mdi-account-circle"></v-icon>
            </v-avatar>
        </v-list-item-avatar>
        </v-col>
        <v-col>
        <v-list-item-content>
            <v-list-item-title>{{comment.text}}</v-list-item-title>
        </v-list-item-content>
        </v-col>
      </v-row>
    <v-list-item-action>
        <v-btn @click="del" small class="button" variant="plain">
            Ответить
        </v-btn>
        <v-btn @click="del" small class="button" variant="plain">
            Удалить
        </v-btn>
    </v-list-item-action>
  </v-list-item>
</template>

<script>
    import { mapActions } from 'vuex'
    export default {
        name: 'CommentItem',
        props: ['comment'],
        methods: {
            ...mapActions(['removeCommentAction']),
            del() {
                this.removeCommentAction(this.comment)
            }
        }
    }
</script>

<style scoped>
.v-img__img{
    size:24;
}
.button{
    font-size:10px
}
</style>