<template>
    <div>
      <!-- Navigation bar component -->
      <NavBar />
  
      <!-- Main content area for the poster details -->
      <section class="poster-detail-container">
        <div v-if="poster">
          <div class="poster-image">
            <img :src="poster.imageUrl" :alt="poster.title" />
          </div>
          <div class="poster-info">
            <h1>{{ poster.title }}</h1>
            <p class="poster-price">\${{ poster.price }}</p>
            <p class="poster-description">{{ poster.description }}</p>
          </div>
        </div>
        <div v-else>
          <p>Loading poster details...</p>
        </div>
      </section>
  
      <!-- Footer component -->
      <FooterComponent />
    </div>
  </template>
  
  <script setup>
  import { ref, onMounted } from 'vue'
  import { useRoute } from 'vue-router'
  
  // Import your existing NavBar and Footer components
  import NavBar from '@/components/NavBar.vue'
  import FooterComponent from '@/components/Footer.vue'
  
  // Register them locally (or you can globally register in main.js)
  const poster = ref(null)
  const route = useRoute()
  
  // Example: fetch poster data by ID (mock or real API call)
  onMounted(async () => {
    // For example, get the poster ID from route params
    const { id } = route.params
  
    // Example: fetch from an API endpoint using the ID
    // Replace this with your actual API or data retrieval logic
    try {
      const response = await fetch(`https://api.example.com/posters/${id}`)
      const data = await response.json()
      poster.value = data
    } catch (error) {
      console.error('Error fetching poster:', error)
    }
  })
  </script>
  
  <style scoped>
  .poster-detail-container {
    max-width: 800px;
    margin: 0 auto;
    padding: 2rem 1rem;
    display: flex;
    gap: 2rem;
  }
  
  .poster-image img {
    width: 100%;
    max-width: 400px;
    object-fit: cover;
  }
  
  .poster-info {
    display: flex;
    flex-direction: column;
    justify-content: center;
  }
  
  .poster-price {
    font-weight: bold;
    margin: 0.5rem 0;
  }
  
  .poster-description {
    margin-top: 1rem;
    line-height: 1.5;
  }
  </style>
  