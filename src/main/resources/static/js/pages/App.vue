<template>
    <div>
    <div v-if="!profile">Необходимо авторизоваться через
        <a href="/login">Google</a>
    </div>
    <div v-else>
        <div>{{profile.username}}&nbsp;<a href="/logout">Выйти</a></div>
        <posts-list :posts="posts" />
        </div>
    </div>
</template>

<script>
    import PostsList from 'components/posts/PostList.vue'
    export default {
        components: {
            PostsList
        },
        data() {
            return {
                posts: frontendData.posts,
                profile: frontendData.profile
            }
        }
    }
</script>

<style>
</style>