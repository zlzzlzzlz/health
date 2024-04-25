import Vue from 'vue'
import Router from 'vue-router'
import Login from '@/pages/Login'
import UserBase from '@/pages/UserBase'
import Home from '@/pages/Home'
import SelectFood from '@/pages/SelectFood'
import Me from '@/pages/Me.vue'
import Water from '@/pages/Water.vue'
import WaterDetail from '@/pages/WaterDetail.vue'
import Report from '@/pages/Report.vue'
import Article from '@/pages/Article.vue'
import ArticleDetail from '@/pages/ArticleDetail.vue'
import Command from '@/pages/Command.vue'
import UploadArticle from'@/pages/UploadAtricle.vue'
import FoodDetail from'@/pages/FoodDetail.vue'
import UpdateBase from'@/pages/UpdateBase.vue'
import Advice from'@/pages/Advice.vue'
import Eat from '@/pages/Eat.vue'
import Registered from '@/pages/Registered.vue'
import EatDetail from '@/pages/EatDetail.vue'
Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'login',
      component: Login
    },
    {
      path: '/eatDetail',
      name: 'eatDetail',
      component: EatDetail
    },
    {
      path: '/updatebase',
      name: 'updatebase',
      component: UpdateBase
    },
    {
      path: '/registered',
      name: 'registered',
      component: Registered
    },
    {
      path: '/advice',
      name: 'advice',
      component: Advice
    },
    {
      path: '/eat',
      name: 'eat',
      component: Eat
    },
    {
      path: '/userbase',
      name: 'userBase',
      component: UserBase
    },
    {
      path: '/foodDetail',
      name: 'foodDetail',
      component: FoodDetail
    },
    {
      path: '/home',
      name: 'home',
      component: Home
    },
    {
      path: '/selectfood',
      name: 'SelectFood',
      component: SelectFood
    },
    {
      path: '/me',
      name: 'Me',
      component: Me
    },
    {
      path: '/water',
      name: 'water',
      component: Water
    },
    {
      path: '/waterDetail',
      name: 'waterDetail',
      component: WaterDetail
    },
    {
      path: '/report',
      name: 'report',
      component: Report
    },
    {
      path: '/article',
      name: 'article',
      component: Article
    },
    {
      path: '/articleDetail',
      name: 'articleDetail',
      component: ArticleDetail
    },
    {
      path: '/command',
      name: 'command',
      component: Command
    },
    {
      path: '/uploadArticle',
      name: 'uploadArticle',
      component: UploadArticle
    },
  ]
})
