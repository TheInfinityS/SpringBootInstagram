<template>
    <div>
        <i>({{ post.id }})</i> {{ post.text }}
        <span style="position: absolute; right: 0">
            <input type="button" value="Edit" @click="edit" />
            <input type="button" value="X" @click="del" />
        </span>
    </div>
</template>

<script>
    export default {
        props: ['post', 'editPost', 'deletePost', 'posts'],
        methods: {
            edit() {
                this.editPost(this.post)
            },
            del() {
                this.deletePost(this.post)
            }
        }
    }
</script>

<style>
</style>