import {createApp} from 'vue'
import * as VueRouter from 'vue-router'
import PostList from 'pages/PostList.vue'
import Auth from 'pages/Auth.vue'
import Profile from 'pages/Profile.vue'

const app = createApp()
app.use(VueRouter)

const routes=[
    {path: '/',component: PostList},
    {path: '/auth',component: Auth},
    {path: '/user/:id?',component: Profile},
    {path: "/:catchAll(.*)",component: PostList},
]

export default VueRouter.createRouter({
    history: VueRouter.createWebHistory(),
    routes
})