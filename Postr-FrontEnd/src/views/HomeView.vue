<!-- src/views/HomeView.vue -->
<template>
  <div class="home-view">
    <NewArrival />
    <BrowseRange />
  </div>
</template>

<script>
import NewArrival from "@/components/NewArrival.vue";
import BrowseRange from "@/components/BrowseRange.vue";

export default {
  name: "HomeView",
  components: {
    NewArrival,
    BrowseRange,
  },
};
</script>

<style scoped>
.home-view {
  overflow-y: auto; /* Allow vertical scrolling */
}
</style>
