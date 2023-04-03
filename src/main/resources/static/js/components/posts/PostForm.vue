<template>
    <v-row>
        <v-col>
            <v-text-field
                label="New post"
                placeholder="Write something"
                v-model="text"
                @keyup.enter="save"/>
            <v-file-input
                accept="image/*"
                label="File input"
                 @change="handleFileUpload( $event )"
              ></v-file-input>
        </v-col>
        <v-btn @click="save">
            Save
        </v-btn>
    </v-row>
</template>

<script>
    import {mapActions} from 'vuex'
    export default {
        props: ['postAttr'],
        data() {
            return {
                text: null,
                id: null,
                file: null,
            }
        },
        watch: {
            postAttr(newVal, oldVal) {
                this.text = newVal.text
                this.id = newVal.id
            }
        },
        methods: {
            ...mapActions(['addPostAction','updatePostAction']),
            save() {
                 const post = {
                    id: this.id,
                    text: this.text
                }
                if (this.id) {
                    this.updatePostAction(post)
                } else {
                    this.addPostAction(post,file)
                }
                this.text = null
                this.id = null
            },
            handleFileUpload(){
                this.file = event.target.files[0];

              }
        }
    }
</script>

<style>
</style>