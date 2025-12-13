import Vue from "vue";
import App from "./App.vue";
import router from "./router";
import store from "./store";
import ElementUI from "element-ui";
import "element-ui/lib/theme-chalk/index.css";
import VueDND from 'awe-dnd'
import './store/interceptor.js'
import iView from 'iview'
import "./css/none.css";
import VueRouter from "vue-router";


Vue.use(VueRouter)
Vue.use(iView)
Vue.prototype.$axios = require('axios')
Vue.use(VueDND)
Vue.prototype.$axios.defaults.baseURL = 'http://localhost:8700'

Vue.config.productionTip = false;
Vue.use(ElementUI);

router.beforeEach((to, from, next) => {
        if (to.meta.requireAuth) {
            if (store.state.Authorization) {
                next()
            } else {
                next({
                    path: '/login',
                })
            }
        } else {
            next()
        }
    }
)


new Vue({
    router,
    store,
    render: h => h(App)
}).$mount("#app");
