// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import Vant from 'vant';
import 'vant/lib/index.css';
import { Calendar } from 'vant';
import { Image as VanImage } from 'vant';
import { Form } from 'vant';
import { NavBar } from 'vant';
import { Cell, CellGroup } from 'vant';
import { AddressList } from 'vant';
import { ContactCard, ContactList, ContactEdit } from 'vant';
import { Divider } from 'vant';
import { AddressEdit } from 'vant';
import { Area } from 'vant';
import { Popup } from 'vant';
import axios from 'axios'
import { Swipe, SwipeItem } from 'vant';
import { Lazyload } from 'vant';
import { Search } from 'vant';
import { Card } from 'vant';
import { ActionSheet } from 'vant';
import { Picker } from 'vant';
import { Grid, GridItem } from 'vant';
import { Uploader } from 'vant';
import { Col, Row } from 'vant';



Vue.use(Col);
Vue.use(Row);

Vue.use(Uploader);
Vue.use(Grid);
Vue.use(GridItem);
Vue.use(Picker);

Vue.use(ActionSheet);

Vue.use(Card);

Vue.use(Search);
Vue.use(Lazyload);
Vue.use(Swipe);
Vue.use(SwipeItem);

Vue.prototype.$axios = axios;
Vue.use(Popup);

Vue.use(Area);

Vue.use(AddressEdit);

Vue.use(Divider);

Vue.use(ContactCard);
Vue.use(ContactList);
Vue.use(ContactEdit);

Vue.use(AddressList);

Vue.use(Cell);
Vue.use(CellGroup);

Vue.use(NavBar);

Vue.use(Form);
Vue.use(VanImage);

Vue.use(Vant);
Vue.use(Calendar);
Vue.config.productionTip = false
// Vue.use(VueRouter)

// 请求拦截
axios.interceptors.request.use(config => {
  if ("prod-api/user/login" != config.url) {
    if (localStorage.getItem("token")) {
      config.headers['Authorization'] = 'Bearer ' + localStorage.getItem("token")
      console.log(localStorage.getItem("token"))
      // 让每个请求携带自定义token 请根据实际情况自行修
    }
  }
  return config;
}, err => {
  // todo 待完善接口拦截
  return Promise.reject(err);
});
/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  components: { App },
  template: '<App/>'
})
