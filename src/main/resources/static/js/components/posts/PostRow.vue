<template>
    <v-card class="my-2" color="black">
        <v-card-item>
            <v-row>
                <v-col
                    cols="auto"
                    class="me-auto"
                >
                    <user-link :user="post.author" size="48"></user-link>
                </v-col>
                <v-col>
                    <router-link :to="`/user/${post.author.id}`">
                        <strong v-html="post.author.username" class="text-white text-decoration-none ml-3"></strong>
                    </router-link>
                </v-col>
                <v-col cols="auto">
                    <v-btn size="small"   icon="mdi-dots-horizontal" class="pa-2 ma-2" variant="plain"></v-btn>
                </v-col>
            </v-row>
        </v-card-item>
        <v-img
          cover
          src="D:/instagram/${post.imageUrl}"
        ></v-img>
        <v-card-actions>
            <v-btn v-if="isMyPost" value="Edit" @click="edit" small flat round>Edit</v-btn>

            <v-btn v-if="isMyPost" icon @click="del" small>
                <v-icon>mdi-delete</v-icon>
            </v-btn>
            <v-btn size="small"   icon="mdi-heart-outline"></v-btn>

            <v-btn size="small"   icon="mdi-bookmark-outline"></v-btn>

            <v-btn size="small"  icon="mdi-comment-outline"></v-btn>
        </v-card-actions>
        <v-card-text primary-title>
            <div class="pt-3">
                <router-link :to="`/user/${post.author.id}`">
                    <strong v-html="post.author.username" class="text-white text-decoration-none ml-3"></strong>
                </router-link>
                {{ post.text }}
            </div>
        </v-card-text>
        <v-divider class="mx-4 mb-1"></v-divider>
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