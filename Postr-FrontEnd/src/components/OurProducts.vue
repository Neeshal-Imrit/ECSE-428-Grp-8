<template>
    <div class="poster-list">
      <h2 class="title">Our Posters</h2>
      <div class="posters">
        <div class="poster-container" v-for="poster in posters" :key="poster.title">
          <img
            v-if="poster.imageData"
            :src="'data:image/jpeg;base64,' + poster.imageData"
            :alt="poster.title"
            class="poster-image"
          />
          <div class="poster-info">
            <h3 class="poster-title">{{ poster.title }}</h3>
            <p class="poster-description">{{ poster.description }}</p>
            <p class="poster-price">${{ poster.price }}</p>
          </div>
        </div>
      </div>
      <div v-if="loading" class="status">Loading...</div>
      <div v-else-if="error" class="status error">Error: {{ error }}</div>
    </div>
  </template>
  
  <script>
  import { onMounted } from 'vue';
  import { usePosterStore } from '@/stores/posterStore';
  import { storeToRefs } from 'pinia';
  
  export default {
    name: 'PosterList',
    setup() {
      const posterStore = usePosterStore();
      // Use storeToRefs to keep reactivity
      const { posters, loading, error } = storeToRefs(posterStore);
  
      onMounted(() => {
        posterStore.fetchAllPosters();
      });
  
      return {
        posters,
        loading,
        error,
      };
    },
  };
  </script>
  
  <style scoped>
  .poster-list {
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
    color: #555;
  }
  
  .posters {
    display: flex;
    flex-wrap: wrap;
    justify-content: center;
    gap: 20px;
  }
  
  .poster-container {
    width: 250px;
    border-radius: 8px;
    overflow: hidden;
    box-shadow: 2px 2px 10px rgba(147, 147, 147, 0.2);
    background-color: #fff;
    transition: transform 0.3s;
  }
  
  .poster-container:hover {
    transform: scale(1.03);
  }
  
  .poster-image {
    width: 100%;
    height: auto;
    display: block;
  }
  
  .poster-info {
    padding: 10px;
    text-align: left;
  }
  
  .poster-title {
    font-size: 18px;
    margin: 0;
    font-weight: bold;
  }
  
  .poster-description {
    font-size: 14px;
    color: #333;
    margin: 5px 0;
  }
  
  .poster-price {
    font-size: 16px;
    color: #b88e2f;
    font-weight: bold;
  }
  
  .status {
    margin-top: 20px;
    font-size: 16px;
  }
  
  .error {
    color: red;
  }
  </style>
  