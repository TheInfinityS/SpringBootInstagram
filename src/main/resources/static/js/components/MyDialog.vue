<template>
    <div class="dialog" v-if="show" @click="hideDialog">
        <div class="dialog_content">
            <slot></slot>
        </div>
    </div>
</template>

<script>
    export default {
        name: 'MyDialog',
        props:{
            show:{
                type:Boolean,
                default:false
            }
        },
        methods:{
            hideDialog(){
                this.$emit('update:show',false)
            }
        }
    }
</script>

<style scoped>
.dialog{
    top:0;
    bottom:0;
    right:0;
    left:0;
    background: rgba(0,0,0,0.5);
    position:fixed;
    display:flex;
}
.dialog_content{
    margin:auto;
    padding:20px;
    background:dark-grey;
    border-radius:12px;
}
</style>