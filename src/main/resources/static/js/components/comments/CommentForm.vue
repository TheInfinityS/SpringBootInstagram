<template>
    <v-row variant="outlined">
        <v-text-field
            label="Add comment"
            placeholder="Write something"
            v-model="text"
            @keyup.enter="save"/>
        <v-btn @click="save">
            Add
        </v-btn>
    </v-row>
</template>

<script>
    import { mapActions } from 'vuex'
    export default {
        name: 'CommentForm',
        props: ['postId'],
        data() {
            return {
                text: ''
            }
        },
        methods: {
            ...mapActions(['addCommentAction']),
            async save() {
                await this.addCommentAction({
                    text: this.text,
                    post: {
                        id: this.postId
                    }
                })
                this.text = ''
            }
        }
    }
</script>

<style scoped>
</style>