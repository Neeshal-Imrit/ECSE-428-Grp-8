<!-- src/views/HomeView.vue -->
<template>
  <div class="home-view">
    <NewArrival />
    <BrowseRange />
    <OurProducts />
    <UploadPoster />
  </div>
</template>

<script>
import NewArrival from "@/components/NewArrival.vue";
import BrowseRange from "@/components/BrowseRange.vue";
import OurProducts from "@/components/OurProducts.vue";
import UploadPoster from "@/components/UploadPoster.vue";

export default {
  name: "HomeView",
  components: {
    NewArrival,
    BrowseRange,
    OurProducts,
    UploadPoster,
  },
};
</script>

<style scoped>
.home-view {
  overflow-y: auto; /* Allow vertical scrolling */
}
</style>
