import Vue from 'vue'
import App from './App.vue'
import router from './router'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import './assets/gloable.css'
import store from "./store";
import request from "@/utils/request";
import VueCookies from 'vue-cookies'

import mavonEditor from 'mavon-editor'
import 'mavon-editor/dist/css/index.css'

Vue.use(mavonEditor)
Vue.use(VueCookies)
Vue.config.productionTip = false
Vue.use(ElementUI, {size: "mini"});
Vue.prototype.request = request;

new Vue({
    router,
    store,
    render: h => h(App)
}).$mount('#app')