import { createApp } from 'vue'
import '@babel/polyfill'
import App from 'pages/App.vue'
import store from 'store/store'
import router from 'router/router'
import { connect } from './util/ws'

import 'vuetify/styles'
import { createVuetify } from 'vuetify'
import * as components from 'vuetify/components'
import * as directives from 'vuetify/directives'

const vuetify = createVuetify({
    theme: {
        defaultTheme: 'dark'
      },
  components,
  directives,
})

if(profile){
    connect()
}
const app = createApp(App)
app.use(vuetify)
app.use(store)
app.use(router)
app.mount('#app')