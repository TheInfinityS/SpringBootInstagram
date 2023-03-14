<template>
    <v-container class="px-16">
        <v-row>
            <v-col align="space-around" justify="start" class="pslist">
                <post-form :postAttr="post" />
                <post-row v-for="post in sortedPosts"
                             :key="post.id"
                             :post="post"
                             :editPost="editPost"/>
                <lazy-loader></lazy-loader>
            </v-col>
            <v-col class="aclist">
                <router-link :to="`/user`">
                    <strong v-html="this.$store.state.profile.username" class="text-white text-decoration-none ml-3"></strong>
                </router-link>
            </v-col>
        </v-row>
    </v-container>
</template>

<script>
    import PostRow from 'components/posts/PostRow.vue'
    import PostForm from 'components/posts/PostForm.vue'
    import LazyLoader from 'components/LazyLoader.vue'
    import { mapGetters } from 'vuex'
    export default {
        name:'PostsList',
        components: {
            PostRow,
            PostForm,
            LazyLoader
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
.pslist{
    max-width: 470px !important;
}
.aclist{
    height: 66px;
    margin-bottom: 30px;
    margin: 0 auto;
    align-items: center;
    display: flex;
}
</style>