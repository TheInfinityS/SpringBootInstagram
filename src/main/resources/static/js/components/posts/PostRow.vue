<template>
    <v-card class="my-2">
        <v-card-text primary-title>
            <div>
                <v-avatar v-if="post.author&&post.author.imageUrl"
                        size="60px">
                  <v-img
                    :src="post.author.imageUrl"
                    :alt="post.author.username"
                  ></v-img>
                </v-avatar>

                <v-avatar v-else
                        size="36px"
                        color="indigo">
                  <v-icon icon="mdi-account-circle"></v-icon>
                </v-avatar>
                <span class="pl-3">{{authorName}}</span>
            </div>
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
    export default {
        props: ['post', 'editPost'],
        components:{CommentList},
        computed:{
            authorName() {
                return this.post.author ? this.post.author.name : 'unknown'
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