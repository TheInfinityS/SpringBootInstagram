<template>
    <v-container>
            Необходимо авторизоваться через
            <a href="/oauth2/authorization/google">Google</a>

            <v-row justify="center">
              <v-col cols="12" sm="8" md="4">
                <v-card>
                  <v-card-title class="headline">Login</v-card-title>
                  <v-card-text>
                    <v-form>
                      <v-text-field v-model="username" label="Username"></v-text-field>
                      <v-text-field v-model="password" label="Password" type="password"></v-text-field>
                    </v-form>
                  </v-card-text>
                  <v-card-actions>
                    <v-btn color="primary" @click="login">Login</v-btn>
                  </v-card-actions>
                </v-card>
              </v-col>
            </v-row>
    </v-container>
</template>

<script>
    export default {
        name:'Auth',
        data() {
            return {
              username: "",
              password: ""
            };
          },
          methods: {
            async login() {
                const result=await fetch('/auth/login', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json;charset=utf-8'
                    },
                    body: JSON.stringify({
                       username: this.username,
                       password: this.password
                     })})
                console.log(result)

            }
          }
    }
</script>

<style>
</style>