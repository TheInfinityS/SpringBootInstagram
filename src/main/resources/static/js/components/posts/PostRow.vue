<template>
    <v-card class="my-2">
        <v-card-text primary-title>
            <user-link :user="post.author" size="48">
            </user-link>
            <div class="pt-3">
                {{ post.text }}
            </div>
        </v-card-text>
        <v-card-actions>
            <v-btn v-if="isMyPost" value="Edit" @click="edit" small flat round>Edit</v-btn>
            <v-btn v-if="isMyPost" icon @click="del" small>
                <v-icon>mdi-delete</v-icon>
            </v-btn>
        </v-card-actions>
        <comment-list
                :comments="post.comments"
                :post-id="post.id"></comment-list>
    </v-card>
</template>

<script>
    import { mapActions } from 'vuex'
    import CommentList from 'components/comments/CommentList.vue'
    import UserLink from 'components/UserLink.vue'
    export default {
        props: ['post', 'editPost'],
        components:{CommentList,UserLink},
        computed:{
            isMyPost(){
                console.log(this.post.author.id)
                console.log(this.$store.state.profile.id)
                return this.post.author.id === this.$store.state.profile.id
            }
        },
        methods: {
            ...mapActions(['removePostAction']),
            edit() {
                this.editPost(this.post)
            },
            del() {
                this.removePostAction(this.post)
            }
        }
    }
</script>

<style>
</style>