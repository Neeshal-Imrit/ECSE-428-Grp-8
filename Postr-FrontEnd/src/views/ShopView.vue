<template>
  <div class="shop">
    <div v-if="loading">Loading posters...</div>
    <div v-else>
      <div v-if="error" class="error">{{ error }}</div>
      <div v-else class="poster-list">
        <div v-for="poster in posters" :key="poster.id" class="poster-card">
          <img :src="poster.url" alt="Poster Image" class="poster-image" />
          <h2>{{ poster.title }}</h2>
          <p>{{ poster.description }}</p>
          <p class="price">{{ formatPrice(poster.price) }}</p>
          <button class="buy-button" @click="buyNow(poster)">Buy Now</button>
        </div>
      </div>
    </div>
  </div>
</template>


<script>
export default {
  name: 'shop',
  data() {
    return {
      posters: [],
      loading: true,
      error: null,
    }
  },
  created() {
    // Uncomment the line below to fetch from backend
    this.fetchPosters();
  },
  methods: {
    async fetchPosters() {
      try {
        // Replace with your backend URL
        const response = await fetch('http://localhost:8080/users/68/purchases')
        if (!response.ok) {
          throw new Error(`HTTP error! Status: ${response.status}`)
        }
        this.posters = await response.json()
      } catch (err) {
        this.error = err.message
      } finally {
        this.loading = false
      }
    },
    formatPrice(value) {
      return new Intl.NumberFormat('en-CA', {
        style: 'currency',
        currency: 'CAD',
        minimumFractionDigits: 2,
        maximumFractionDigits: 2
      }).format(value);
    },
    buyNow(poster) {
      alert(`You have selected "${poster.title}" for ${this.formatPrice(poster.price)}.`);
      // TODO: Implement actual checkout flow (e.g., navigate to a checkout page or open a payment modal)
    }
  }
}
</script>

<style scoped>
.posters {
  padding: 20px;
}
.poster-list {
  display: flex;
  flex-wrap: nowrap; /* Ensures posters wrap to the next row when needed */
  justify-content: center; /* Centers posters in the row */
  gap: 10px; /* Adds spacing between posters */
  max-width: 100%;;
}
.poster-card {
  border: 1px solid #ccc;
  border-radius: 4px;
  padding: 15px;
  width: 300px;
  text-align: center;
  box-shadow: 2px 2px 8px rgba(0,0,0,0.1);
}
.poster-image {
  width: 100%;
  height: auto;
  margin-bottom: 10px;
}
.price {
  font-weight: bold;
  color: green;
}
.error {
  color: red;
}
.buy-button {
  background-color: #007bff;
  color: white;
  border: none;
  padding: 10px;
  border-radius: 5px;
  cursor: pointer;
  font-size: 14px;
  width: 100%;
}
</style>
