<template>
    <v-app>
        <v-card>
            <v-layout>
                  <v-navigation-drawer
                    permanent
                    theme="dark"
                    color="black"
                  >
                    <v-list nav>
                      <v-list-item prepend-icon="mdi-home" @click="showPosts" v-if="profile" :disabled="$route.path === '/'" title="Главная" variant="plain">
                      </v-list-item>

                      <v-list-item v-if="profile" :disabled="$route.path === '/profile'" @click="showProfile" variant="plain">
                        <v-avatar v-if="profile&&profile.imageUrl"
                                size="40px">
                          <v-img
                            :src="profile.imageUrl"
                          ></v-img>
                        </v-avatar>
                        Профиль
                      </v-list-item>

                      <v-list-item prepend-icon="mdi-exit-to-app" value="clockin" v-if="profile" href="/logout" title="Выйти" variant="plain">
                      </v-list-item>

                    </v-list>
                  </v-navigation-drawer>
                <v-main>
                    <router-view></router-view>
                </v-main>
            </v-layout>
        </v-card>
    </v-app>
</template>

<script>
    import { mapState, mapMutations } from 'vuex'
    import { addHandler } from 'util/ws'
    export default {
        computed: mapState(['profile']),
        methods: {
            ...mapMutations([
                'addPostMutation',
                'updatePostMutation',
                'removePostMutation',
                'addCommentMutation'
            ]),
            showPosts() {
                this.$router.push('/')
            },
            showProfile() {
                this.$router.push('/profile')
            }
        },
        created() {
            addHandler(data => {
                if (data.objectType === 'POST') {
                    switch (data.eventType) {
                        case 'CREATE':
                            this.addPostMutation(data.body)
                            break
                        case 'UPDATE':
                            this.updatePostMutation(data.body)
                            break
                        case 'REMOVE':
                            this.removePostMutation(data.body)
                            break
                        default:
                            console.error(`Looks like the event type if unknown "${data.eventType}"`)
                    }
                } else if (data.objectType === 'COMMENT') {
                    switch (data.eventType) {
                        case 'CREATE':
                            this.addCommentMutation(data.body)
                            break
                        default:
                            console.error(`Looks like the event type if unknown "${data.eventType}"`)
                    }
                } else {
                    console.error(`Looks like the object type if unknown "${data.objectType}"`)
                }
            })
        },
        beforeMount() {
            if (!this.profile) {
                this.$router.replace('/auth')
            }
        }
    }
</script>

<style>
</style>