<template>
    <v-container>
        <v-col align="space-around" justify="start">
            <post-form :postAttr="post" />
            <post-row v-for="post in sortedPosts"
                         :key="post.id"
                         :post="post"
                         :editPost="editPost" />
        </v-col>
    </v-container>
</template>

<script>
    import PostRow from 'components/posts/PostRow.vue'
    import PostForm from 'components/posts/PostForm.vue'
    import { mapGetters } from 'vuex'
    export default {
        name:'PostsList',
        components: {
            PostRow,
            PostForm
        },
        data() {
            return {
                post: null
            }
        },
        computed:
            mapGetters(['sortedPosts']),
        methods: {
            editPost(post) {
                this.post = post
            }
        }
    }
</script>

<style>
</style>