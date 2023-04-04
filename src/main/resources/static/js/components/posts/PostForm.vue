<template>
    <v-row>
        <v-col>
            <v-text-field
                label="New post"
                placeholder="Write something"
                v-model="text"
                @keyup.enter="save"/>
            <v-file-input
              ref="fileInput"
              v-model="file"
              show-size
              label="File input"
              @click:prepend="resetFileInput"
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
                file : null
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
                    let formData = new FormData()
                    formData.append('text',post.text)
                    formData.append('file', this.file)
                    this.addPostAction(formData);
                }
                this.text = null
                this.id = null
                this.file=null
            },
            handleFileUpload( e ){
                this.file = e.target.files[0];
            },
            resetFileInput() {
              this.$refs.fileInput.reset();
            }
        }
    }
</script>

<style>
</style>