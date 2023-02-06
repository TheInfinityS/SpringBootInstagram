<template>
    <div>
        <input type="text" placeholder="Write something" v-model="text" />
        <input type="button" value="Save" @click="save" />
    </div>
</template>

<script>
    function getIndex(list, id) {
        for (var i = 0; i < list.length; i++ ) {
            if (list[i].id === id) {
                return i
            }
        }
        return -1
    }
    export default {
        props: ['posts', 'postAttr'],
        data() {
            return {
                text: '',
                id: ''
            }
        },
        watch: {
            postAttr(newVal, oldVal) {
                this.text = newVal.text
                this.id = newVal.id
            }
        },
        methods: {
            save() {
                const post = { text: this.text }
                if (this.id) {
                post.id=this.id
                console.log(post)
                    this.$fetch('/post/'+this.id, {
                                     method: 'PUT',
                                     headers: {
                                       'Content-Type': 'application/json;charset=utf-8'
                                     },
                                     body: JSON.stringify(post)}).then(result =>
                        result.json().then(data => {
                            const index = getIndex(this.posts, data.id)
                            this.posts.splice(index, 1, data)
                            this.text = ''
                            this.id = ''
                        })
                    )
                } else {
                    this.$fetch('/post', {
                                     method: 'POST',
                                     headers: {
                                         'Content-Type': 'application/json;charset=utf-8'
                                     },
                                     body: JSON.stringify(post)}).then(result =>
                        result.json().then(data => {
                            this.posts.push(data)
                            this.text = ''
                        })
                    )
                }
            }
        }
    }
</script>

<style>
</style>