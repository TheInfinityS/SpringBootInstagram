<template>
    <v-container>
        <v-row justify="space-around"  class="ac">
            <v-col>
                <v-row>
                    <div class="px-1 aprfl">
                        <v-avatar size="150">
                            <v-img
                                :src="profile.imageUrl"
                                cover
                                ></v-img>
                        </v-avatar>
                    </div>
                    <div class="px-1 infprfl">
                        <v-col>
                            <v-row justify="start">
                                <v-col cols="2">
                                    <v-sheet class="text-h5">{{profile.username}}</v-sheet >
                                </v-col>
                                <v-col>
                                    <v-btn
                                        v-if="!isMyProfile"
                                        @click="changeFollowing"
                                    >
                                        {{isIFollower ? 'Unfollow':'Follow'}}
                                    </v-btn>
                                    <v-btn append-icon="mdi-cog" variant="tonal" v-else>Edit profile</v-btn>
                                </v-col>
                            </v-row>
                            <v-row justify="start">
                                <v-col cols="3">
                                    <v-sheet class="text-white text-decoration-none ml-3">{{userPosts.length}} posts</v-sheet>
                                </v-col>
                                <v-col>
                                    <router-link v-if="isMyProfile" :to="'/follower/${profile.id}'" class="text-white text-decoration-none ml-3">
                                        {{profile.followers&&profile.followers.length}} followers
                                    </router-link>
                                    <v-sheet v-else>
                                        {{profile.followers&&profile.followers.length}} followers
                                    </v-sheet >
                                </v-col>
                                <v-col>
                                    <router-link v-if="isMyProfile" :to="'/following/${profile.id}'" class="text-white text-decoration-none ml-3">
                                        {{profile.followings&&profile.followings.length}} following
                                    </router-link>
                                    <v-sheet v-else>
                                        {{profile.followings&&profile.followings.length}} following
                                    </v-sheet >
                                </v-col>
                            </v-row>
                            <v-row justify="start">
                                <v-col cols="1">
                                    <v-sheet>{{profile.fullName}}</v-sheet >
                                </v-col>
                            </v-row>
                        </v-col>
                    </div>
                </v-row>
            </v-col>
        </v-row>
        <v-divider class="mx-4 mb-1"></v-divider>
        <div>Ok</div>
    </v-container>
</template>

<script>
    export default {
        name: 'Profile',
        data(){
            return{
                profile:{},
                userPosts:[]
            }
        },
        computed:{
            isMyProfile(){
                return !this.$route.params.id ||
                                    this.$route.params.id == this.$store.state.profile.id
            },
            isIFollower(){
                return this.profile.followers && this.profile.followers.find(following => {
                    return following.follower === this.$store.state.profile.id
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

                const response= await fetch('/post/user/'+id,{method: 'GET'})
                this.userPosts=await response.json()

                this.$forceUpdate()
            }
        },
        async beforeMount(){
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
    .aprfl{
        margin-right: 30px;
        flex-grow: 1;
        flex-basis: 0;
    }
    .infprfl{
        flex-grow: 2;
    }
    .ac{
        margin-bottom: 44px !important;
    }
</style>