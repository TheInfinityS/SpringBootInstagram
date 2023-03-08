<template>
  <v-list-item>
      <v-row justify="start">
        <v-col cols="1">
        <v-list-item-avatar>
            <user-link :user="comment.author" size=36>
            </user-link>
        </v-list-item-avatar>
        </v-col>
        <v-col >
        <v-list-item-content>
            <v-list-item-title><b>{{comment.author.username}}</b>  {{comment.text}}</v-list-item-title>
        </v-list-item-content>
        <v-list-item-action>
        <v-btn @click="del" small class="button" variant="plain">
            Ответить
        </v-btn>
        <v-btn @click="del" small class="button" variant="plain">
            Удалить
        </v-btn>
    </v-list-item-action>
        </v-col>
      </v-row>
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