<template>
  <div class="browse-range">
    <h2 class="title">Browse The Range</h2>
    <h4 class="subtitle">Explore our latest collections</h4>
    <div class="images">
      <div class="image-container">
        <img
          src="@/assets/POSTER-holland-market.jpg"
          alt="Image 1"
          class="image"
        />
        <p class="image-subtitle">Holland Market</p>
      </div>
      <div class="image-container">
        <img src="@/assets/POSTER-be-brave.jpg" alt="Image 2" class="image" />
        <p class="image-subtitle">Be Brave</p>
      </div>
      <div class="image-container">
        <img src="@/assets/POSTER-sun&blue.jpg" alt="Image 3" class="image" />
        <p class="image-subtitle">Sun & Blue</p>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "BrowseRange",
};
</script>

<style scoped>
.browse-range {
  text-align: center;
  padding: 20px;
  margin: 10px;
}

.title {
  font-size: 30px;
  margin-top: 20px;
  font-weight: bold;
}

.subtitle {
  font-size: 14px;
  margin-bottom: 20px;
}

.images {
  display: flex;
  justify-content: center; /* Center images */
}

.image-container {
  align-items: center;
  margin: 0;
}

.image {
  width: 50%;
  height: auto;
  border-radius: 8px;
  box-shadow: 2px 2px 10px rgba(147, 147, 147, 0.2);
  margin: 0;
}

.image-subtitle {
  margin-top: 10px;
  font-size: 14px;
  color: #333;
}
</style>
