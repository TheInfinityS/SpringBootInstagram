import {createApp} from 'vue'
import * as VueRouter from 'vue-router'
import PostList from 'pages/PostList.vue'
import Auth from 'pages/Auth.vue'
import Profile from 'pages/Profile.vue'
import Following from 'pages/Following.vue'
import Follower from 'pages/Follower.vue'

const app = createApp()
app.use(VueRouter)

const routes=[
    {path: '/',component: PostList},
    {path: '/auth',component: Auth},
    {path: '/user/:id?',component: Profile},
    {path: "/following/:id",component: Following},
    {path: "/follower/:id",component: Follower},
    {path: "/:catchAll(.*)",component: PostList},
]

export default VueRouter.createRouter({
    history: VueRouter.createWebHistory(),
    routes
})