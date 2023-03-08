<template>
    <v-container>
        <v-row justify="space-around">
            <v-col>
                <v-row>
                    <v-card class="title mb-3">User profile</v-card>
                </v-row>
                <v-row>
                    <v-card class="px-1">
                        <v-img
                            :src="profile.imageUrl"
                            cover
                            width="125"
                            ></v-img>
                    </v-card>
                    <v-card class="px-1">
                        <v-col>
                            <v-sheet >{{profile.name}}</v-sheet >
                            <v-sheet >{{profile.locale}}</v-sheet >
                            <v-sheet >{{profile.gender}}</v-sheet >
                            <v-sheet >{{profile.lastVisit}}</v-sheet >
                            <v-sheet >{{profile.followings&&profile.followings.length}} following</v-sheet >
                            <v-sheet >{{profile.followers&&profile.followers.length}} followers</v-sheet >
                        </v-col>
                    </v-card>
                </v-row>
                <v-btn
                    v-if="!isMyProfile"
                    @click="changeFollowing"
                >
                    {{isIFollower ? 'Unfollow':'Follow'}}
                </v-btn>
            </v-col>
        </v-row>
    </v-container>
</template>

<script>
    export default {
        name: 'Profile',
        data(){
            return{
                profile:{}
            }
        },
        computed:{
            isMyProfile(){
                return !this.$route.params.id ||
                                    this.$route.params.id == this.$store.state.profile.id
            },
            isIFollower(){
                return this.profile.followers && this.profile.followers.find(following => {
                    return following.id === this.$store.state.profile.id
                })
            }
        },
        watch:{
            '$route'(){
                this.updateProfile()
            }
        },
        methods:{
            async changeFollowing(){
                const data = await fetch('/profile/change-following/'+this.profile.id,{method: 'POST'})
                this.profile = await data.json()
            },
            async updateProfile(){
                let id = parseInt(this.$route.params.id || this.$store.state.profile.id)
                const data = await fetch('/profile/'+id,{method: 'GET'})
                this.profile = await data.json()

                this.$forceUpdate()
            }
        },
        beforeMount(){
            this.updateProfile()
        }
    }
</script>

<style>
    img {
        width:100%;
        height:100%;
    }
    .v-image__image{
     background-size:100% 100%;
    }
</style>