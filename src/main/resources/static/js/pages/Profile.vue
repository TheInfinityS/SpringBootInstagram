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
                            <v-sheet >{{profile.email}}</v-sheet >
                            <v-sheet >{{profile.lastVisit}}</v-sheet >
                        </v-col>
                    </v-card>
                </v-row>
            </v-col>
        </v-row>
    </v-container>
</template>

<script>
    import { mapState } from 'vuex'
    export default {
        name: 'Profile',
        computed: mapState(['profile'])
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