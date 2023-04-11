import { createApp } from 'vue'
import { createStore } from 'vuex'

export default createStore({
  state () {
    return {
        posts,
        profile,
        ...frontendData
    }
  },
  getters: {
    sortedPosts: state => (state.posts || []).sort((a, b) => -(a.id - b.id))
  },
  mutations: {
    addPostMutation(state, post){
        state.posts=[
            ...state.posts,
            post
        ]
    },
    updatePostMutation(state, post){
        const updateIndex= state.posts.findIndex(item=>item.id===post.id)
        state.posts=[
            ...state.posts.slice(0,updateIndex),
            post,
            ...state.posts.slice(updateIndex+1)
        ]
    },
    likePostMutation(state,post){
        const updateIndex= state.posts.findIndex(item=>item.id===post.id)
        state.posts=[
            ...state.posts.slice(0,updateIndex),
            post,
            ...state.posts.slice(updateIndex+1)
        ]
    },
    removePostMutation(state, post){
        const deletionIndex= state.posts.findIndex(item=>item.id===post.id)
        if(deletionIndex>-1){
            state.posts=[
                ...state.posts.slice(0,deletionIndex),
                ...state.posts.slice(deletionIndex+1)
            ]
        }

    },
    addCommentMutation(state, comment) {
        const updateIndex = state.posts.findIndex(item => item.id === comment.post.id)
        const post = state.posts[updateIndex]

        if(!post.comments.find(it=>it.id===comment.id)){
            state.posts = [
                ...state.posts.slice(0, updateIndex),
                {
                    ...post,
                    comments: [
                        ...post.comments,
                        comment
                    ]
                },
                ...state.posts.slice(updateIndex + 1)
            ]
        }
    },
    likeCommentMutation(state, comment){
        const updateIndex = state.posts.findIndex(item => item.id === comment.post.id)

        const post = state.posts[updateIndex]
        const commentIndex=post.comments.findIndex(item=>item.id===comment.id)
        console.log(commentIndex)

        if(!post.comments.find(it=>it.id===comment.id)){
            state.posts = [
                ...state.posts.slice(0, updateIndex),
                {
                    ...post,
                    comments: [
                        ...post.comments.slice(0,commentIndex),
                        comment,
                        ...post.comments.slice(commentIndex+1)
                    ]
                },
                ...state.posts.slice(updateIndex + 1)
            ]
        }
    },
    removeCommentMutation(state, comment){
         const updateIndex = state.posts.findIndex(item => item.id === comment.post.id)
         const post = state.posts[updateIndex]
         const deletionIndex= state.posts[updateIndex].comments.findIndex(item=>item.id===comment.id)
         if(deletionIndex>-1){
             state.posts=[
                 ...state.posts.slice(0, updateIndex),
                 {
                     ...post,
                     comments: [
                         ...post.comments.slice(0,deletionIndex),
                         ...post.comments.slice(deletionIndex+1)
                     ]
                 },
                 ...state.posts.slice(updateIndex + 1)
             ]
         }
     },
     addPostPageMutation(state,posts){
        const targetPosts=state.posts
            .concat(posts)
            .reduce((res,val)=>{
                res[val.id]=val
                return res
            }, {})
        state.posts=Object.values(targetPosts)
     },
     updateTotalPagesMutation(state,totalPages){
        state.totalPages=totalPages
     },
     updateCurrentPageMutation(state,currentPage){
        state.currentPage=currentPage
     }
  },
  actions: {
    async addPostAction({commit,state},formData){

        const result=await fetch('/post', {
            method: 'POST',
            body: formData})
        const data=await result.json()

        const index = state.posts.findIndex(item=>item.id === data.id)
        if(index!=null){
            commit('updatePostMutation', data)
        }
        else{
            commit('addPostMutation', data)
            }
    },
    async updatePostAction({commit}, post){
        console.log(post)
        const result=await fetch('/post/'+post.id, {
            method: 'PUT',
            headers: {
              'Content-Type': 'application/json;charset=utf-8'
            },
            body: JSON.stringify(post)})
        const data=await result.json()
        commit('updatePostMutation', data)
    },
    async removePostAction({commit}, post){
        const result=await fetch('/post/'+post.id,{method: 'DELETE'})
        if (result.ok) {
            commit('removePostMutation', post)
        }
    },
    async addCommentAction({commit,state}, comment){
        const result=await fetch('/comment', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json;charset=utf-8'
            },
            body: JSON.stringify(comment)})
        const data=await result.json()

        commit('addCommentMutation', data)
    },
    async removeCommentAction({commit}, comment){
        const result=await fetch('/comment/'+comment.id,{method: 'DELETE'})
        if (result.ok) {
            commit('removeCommentMutation', comment)
        }
    },
    async loadPageAction({commit,state}){
        const result=await fetch('/post?'+ new URLSearchParams({
                                                page: state.currentPage+1
                                            }),{method: 'GET'})
        const data=await result.json()
        commit('addPostPageMutation',data.posts)
        commit('updateTotalPagesMutation',data.totalPages)
        commit('updateCurrentPageMutation',Math.min(data.currentPage,data.totalPages-1))
    },
    async likePostAction({commit}, post){
        const result = await fetch('/post/like/'+post.id,{
           method: 'POST',
           headers: {
                         'Content-Type': 'application/json;charset=utf-8'
                       },
           body: JSON.stringify(post)})
        const data=await result.json()
        commit('likePostMutation', data)
    },
    async likeCommentAction({commit}, comment){
        const result = await fetch('/comment/like/'+comment.id,{
           method: 'POST',
           headers: {
                         'Content-Type': 'application/json;charset=utf-8'
                       },
           body: JSON.stringify(comment)})
        const data=await result.json()
        commit('likeCommentMutation', data)
    }
  }
})