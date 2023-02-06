<template>
    <div>
        <input type="Username" placeholder="username" v-model="username"/>
        <input type="Username" placeholder="username" v-model="username"/>
        <input type="button" value="Save" @click="save" />
    </div>
</template>

<script>
    import MessagesList from 'Registration.vue'
    export default {
        components: {
            Registration
        },
        data() {
            return {
                profile: frontendData.profile
            }
        }
    }

</script>

<style>
</style>