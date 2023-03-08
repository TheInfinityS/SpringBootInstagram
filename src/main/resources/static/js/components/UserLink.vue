<template>
    <router-link :to="`/user/${user.id}`" class="px-3">
        <v-avatar v-if="user&&user.imageUrl"
                :size="`${size || 36}px`">
          <v-img
            :src="user.imageUrl"
            :alt="user.username"
          ></v-img>
        </v-avatar>

        <v-avatar v-else
                :size="'${size}px'"
                color="indigo">
          <v-icon icon="mdi-account-circle"></v-icon>
        </v-avatar>
        <span class="pl-3">{{userName}}</span>
    </router-link>
</template>

<script>
    export default {
        name:'UserLink',
        props:['user','size'],
        computed:{
            userName() {
                return this.user ? this.user.username : 'unknown'
            },
            userId() {
                return this.user ? Number(this.user.id) : null
            }
        },
    }
</script>

<style>
</style>