import { createRouter, createWebHistory } from "vue-router";
import HomeView from "@/views/HomeView.vue";
import ShopView from "@/views/ShopView.vue";
import PostersView from "@/views/MyPostersView.vue";

import UploadPosterView from "@/views/UploadPoster.vue";
import SignupView from "@/views/SignupView.vue";
import SigninView from "@/views/SigninView.vue";
import PosterDetail from "@/views/PosterDetail.vue";
import UpdatePosterView from "@/views/UpdatePosterView.vue";
import UserHistory from "@/views/PurchaseHistoryView.vue"

const routes = [
  { path: "/", name: "home", component: HomeView },
  { path: "/shop", name: "shop", component: ShopView },
  { path: "/my-posters", name: "my-posters", component: PostersView },

  { path: "/uploadposter", name: "upload-poster", component: UploadPosterView },

  { path: "/signup", name: "signup", component: SignupView },
  { path: "/signin", name: "signin", component: SigninView },
  { path: "/posterdetail", name: "posterdetail", component: PosterDetail },
  { path: "/updateposter", name: "updateposter", component: UpdatePosterView },
  { path: "/purchasehistory/:userId", name: "PurchaseHistory", component: UserHistory, props: true },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;
