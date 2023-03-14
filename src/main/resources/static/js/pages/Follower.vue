<template>
    <v-container  class="d-flex justify-center">
        <v-col align="space-around" justify="start">
            <v-list>
              <v-list-subheader justify="center" class="text-center">Followers</v-list-subheader>
                  <v-list-item v-for="item in followers">
                        <v-row justify="center">
                          <v-col cols="2">
                          <v-list-item-avatar>
                              <user-link :user="item.follower" size="24">
                              </user-link>
                          </v-list-item-avatar>
                          </v-col>
                          <v-col>
                            <v-btn v-if="!item.active" @click="changeFollowingStatus(item.follower.id)">
                                Approve
                            </v-btn>
                            <v-btn
                                @click="changeFollowing(item.follower.id)"
                            >
                                Remove
                            </v-btn>
                          </v-col>
                        </v-row>
                  </v-list-item>
            </v-list>
        </v-col>
    </v-container>
</template>

<script>
    import UserLink from 'components/UserLink.vue'
    export default {
        name:'Follower',
        components:{UserLink},
        data(){
            return{
                followers:[]
            }
        },
        methods: {
            async changeFollowingStatus(followerId) {
                await fetch('/profile/change-status/'+followerId,{method: 'POST'})

                const followerIndex = this.followers.findIndex(item =>
                    item.follower.id === followerId
                )
                const follower = this.followers[followerIndex]
                this.followers = [
                    ...this.followers.slice(0, followerIndex),
                    {
                        ...follower,
                        active: !follower.active
                    },
                    ...this.followers.slice(followerIndex + 1)
                ]
            },
            async changeFollowing(followerId){
                const data = await fetch('/profile/change-follower/'+followerId,{method: 'POST'})
                this.profile = await data.json()
            },
        },
        async beforeMount(){
            const response= await fetch('/profile/get-followers/'+this.$store.state.profile.id,{method: 'GET'})
            this.followers=await response.json()
        }
    }
</script>

<style>
</style>