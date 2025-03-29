<template>
  <div class="home-view">
    <NewArrival />
    <BrowseRange />
    <OurProducts />
    <UploadPoster />
  </div>

  <p class="welcome-message">Welcome to Postr!</p>
</template>

<script setup>
import { userEmail } from "@/auth.js";
import NewArrival from "@/components/NewArrival.vue";
import BrowseRange from "@/components/BrowseRange.vue";
import OurProducts from "@/components/OurProducts.vue";
import UploadPoster from "@/components/UploadPoster.vue";
</script>

<style scoped>
.welcome-message {
  font-size: 1.2rem;
  font-weight: bold;
  text-align: center;
  margin-bottom: 10px;
}

.home-view {
  overflow-y: auto;
}
</style>
