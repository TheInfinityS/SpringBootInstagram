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
            <v-btn value="Edit" @click="edit" small flat round>Edit</v-btn>
            <v-btn icon @click="del" small>
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