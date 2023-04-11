<template>
  <v-list-item>
      <v-row justify="start">
        <v-col cols="1">
        <v-list-item-avatar>
            <user-link :user="comment.author" size="36">
            </user-link>
        </v-list-item-avatar>
        </v-col>
        <v-col >
        <v-list-item-content>
            <v-list-item-title>
                <router-link :to="`/user/${comment.author.id}`">
                    <strong v-html="comment.author.username" class="text-white text-decoration-none ml-3"></strong>
                </router-link>
                {{comment.text}}
            </v-list-item-title>
        </v-list-item-content>
        <v-list-item-action>
        <v-btn @click="del" small class="button" variant="plain">
            Ответить
        </v-btn>
        <v-btn v-if="isMyComment" @click="del" small class="button" variant="plain">
            Удалить
        </v-btn>
        <v-btn size="small" @click="like" :color="buttonColor" variant="plain"> 
                <v-icon>{{ isCommentLiked ? 'mdi-heart' : 'mdi-heart-outline' }}</v-icon>
            </v-btn>
            <v-btn size="small" @click="likers" variant="plain">{{comment.likes.length}}</v-btn>
    </v-list-item-action>
        </v-col>
      </v-row>
  </v-list-item>
</template>

<script>
    import { mapActions } from 'vuex'
    import UserLink from 'components/UserLink.vue'
    export default {
        name: 'CommentItem',
        components:{UserLink},
        props: ['comment'],
        computed:{
            isMyComment(){
                return (this.comment.author.id === this.$store.state.profile.id) || (this.comment.author.id === this.$store.state.profile.id)
            },
            isPostLiked(){
                return this.comment.likes.find(likes=>{
                    return  likes.user.id === this.$store.state.profile.id
                })
            },
        },
        methods: {
            ...mapActions(['removeCommentAction','likeCommentAction']),
            del() {
                this.removeCommentAction(this.comment)
            },
            like(){
                this.likeCommentAction(this.comment)
            },
            likers(){
                this.comment.likes.forEach(element => console.log(element.user))
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