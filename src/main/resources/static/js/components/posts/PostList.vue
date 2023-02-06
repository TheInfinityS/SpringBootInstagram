<template>
    <div style="position: relative; width: 300px;">
        <post-form :posts="posts" :postAttr="post" />
        <post-row v-for="post in posts"
                     :key="post.id"
                     :post="post"
                     :editPost="editPost"
                     :deletePost="deletePost"
                     :posts="posts" />
    </div>
</template>

<script>
    import PostRow from 'components/posts/PostRow.vue'
    import PostForm from 'components/posts/PostForm.vue'
    export default {
        props: ['posts'],
        components: {
            PostRow,
            PostForm
        },
        data() {
            return {
                post: null
            }
        },
        methods: {
            editPost(post) {
                this.post = post
            },
            deletePost(post) {

                this.$fetch('/post/'+postAttr.id,{method: 'DELETE'}).then(result => {
                    if (result.ok) {
                        this.posts.splice(this.posts.indexOf(post), 1)
                    }
                })
            }
        }
    }
</script>

<style>
</style>