<template>
  <div class="home-view">
    <NewArrival />
    <BrowseRange />
    <OurProducts />
    <UploadPoster />
  </div>

<!-- Example -- Show the welcome message only if the user is logged in -->
<!--  <p v-if="userEmail" class="welcome-message">Welcome, {{ userEmail }}!</p>  -->
  <p class="welcome-message">Welcome to Postr!</p>
</template>

<script setup>
import { userEmail } from "@/auth.js"; // Import userEmail
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
  overflow-y: auto; /* Allow vertical scrolling */
}
</style>
