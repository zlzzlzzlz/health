import Vue from 'vue'
import Router from 'vue-router'
import HelloWorld from '@/components/HelloWorld'
import Login from '@/pages/Login'
import Home from '@/components/Home'
import userManages from '@/pages/userManages/userManages'
import customerManages from '@/pages/customerManages/CustomerManages'
import boxManages from '@/pages/boxManages/boxManages'
import adManages from '@/pages/adManages/AdManages'
import opinionManages from '@/pages/opinionManages/OpinionManages'
import orderManages from '@/pages/orderManages/orderManages'
import Box2Manages from "@/pages/boxManages/Box2Manages";
Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'login',
      component: Login
    },
    // {
    //   path: '/sidebar',
    //   name: 'sidebar',
    //   component: Sidebar
    // },
    // {
    //   path: '/header',
    //   name: 'header',
    //   component: Header
    // },
    {
      path: '/home',
      name: 'home',
      component: Home,
      children: [{
        path: 'userManages',
        name: 'userManages',
        component: userManages
      },
      {
        path: 'customerManages',
        name: 'customerManages',
        component: customerManages
      },
      {
        path: 'boxManages',
        name: 'boxManages',
        component: boxManages
      },
      {
        path: 'adManages',
        name: 'adManages',
        component: adManages
      },
      {
        path: 'opinionManages',
        name: 'opinionManages',
        component: opinionManages
      },

      {
        path: 'orderManages',
        name: 'orderManages',
        component: orderManages
      },
      {
        path: "/box2Manages",
        name: "box2Manages",
        component: Box2Manages
      }

      ]
    },
  ]
})
