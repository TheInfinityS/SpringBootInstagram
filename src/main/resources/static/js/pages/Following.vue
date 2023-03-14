<template>
    <v-container>
        <v-col align="center" justify="start">
            <v-list>
              <v-list-subheader>Following</v-list-subheader>
                  <v-list-item v-for="item in followings">
                        <v-row justify="center">
                          <v-col cols="2">
                          <v-list-item-avatar>
                              <user-link :user="item.channel" size="24">
                              </user-link>
                          </v-list-item-avatar>
                          </v-col>
                          <v-spacer></v-spacer>
                            <v-col>
                              <v-btn
                                  @click="changeFollowing(item.channel.id)"
                              >
                                  Following
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
            async changeFollowing(channelId){
                const data = await fetch('/profile/change-following/'+channelId,{method: 'POST'})
                this.profile = await data.json()
            },
        },
        async beforeMount(){
            const response= await fetch('/profile/get-followings/'+this.$store.state.profile.id,{method: 'GET'})
            this.followings=await response.json()
        }
    }
</script>

<style>
</style>