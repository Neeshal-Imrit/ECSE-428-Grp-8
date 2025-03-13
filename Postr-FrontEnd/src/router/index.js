import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '@/views/HomeView.vue'
import ShopView from '@/views/ShopView.vue'
import PostersView from '@/views/MyPostersView.vue'
import UploadPosterView from '@/views/UploadPoster.vue'


const routes = [
  { path: '/', name: 'home', component: HomeView },
  { path: '/shop', name: 'shop', component: ShopView },
  { path: '/my-posters', name: 'my-posters', component: PostersView },
  { path: '/uploadposter', name: 'upload-poster', component: UploadPosterView },
]

const router = createRouter({
  history: createWebHistory(),
  routes,
})

export default router
