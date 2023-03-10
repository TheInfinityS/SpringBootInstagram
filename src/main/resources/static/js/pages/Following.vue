<template>
    <v-container>
        <v-col align="space-around" justify="start">
            <v-list>
              <v-list-subheader>Following</v-list-subheader>
                  <v-list-item v-for="item in followings">
                        <v-row justify="start">
                          <v-col cols="1">
                          <v-list-item-avatar>
                              <user-link :user="item.follower" size="24">
                              </user-link>
                          </v-list-item-avatar>
                          </v-col>
                          <v-col >
                          <v-list-item-content>
                              <v-list-item-title>{{item.follower.username}}</v-list-item-title>
                          </v-list-item-content>
                          </v-col>
                          <v-col>
                            <v-btn @click="changeFollowingStatus(item.follower.id)">
                                {{item.active? "Dismiss" : "Approve"}}
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
        name:'Following',
        components:{UserLink},
        data(){
            return{
                followings:[]
            }
        },
        methods: {
            async changeFollowingStatus(followerId) {
                await fetch('/profile/change-status/'+followerId,{method: 'POST'})

                const followingIndex = this.followings.findIndex(item =>
                    item.follower.id === followerId
                )
                const following = this.followings[followingIndex]
                this.followings = [
                    ...this.followings.slice(0, followingIndex),
                    {
                        ...following,
                        active: !following.active
                    },
                    ...this.followings.slice(followingIndex + 1)
                ]
            }
        },
        async beforeMount(){
            const response= await fetch('/profile/get-followers/'+this.$store.state.profile.id,{method: 'GET'})
            this.followings=await response.json()
        }
    }
</script>

<style>
</style>