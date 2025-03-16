import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '@/views/HomeView.vue'
import ShopView from '@/views/ShopView.vue'
import PostersView from '@/views/MyPostersView.vue'
import SignupView from '@/views/SignupView.vue'
import SigninView from '@/views/SigninView.vue'


const routes = [
  { path: '/', name: 'home', component: HomeView },
  { path: '/shop', name: 'shop', component: ShopView },
  { path: '/my-posters', name: 'my-posters', component: PostersView },
  { path: '/signup', name: 'signup', component: SignupView },
  { path: '/signin', name: 'signin', component: SigninView },
]

const router = createRouter({
  history: createWebHistory(),
  routes,
})

export default router
