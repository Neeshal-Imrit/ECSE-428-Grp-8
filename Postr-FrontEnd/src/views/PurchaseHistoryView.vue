<template>
    <div class="PurchaseHistory">
      <h2>Your Purchase History</h2>
      <div v-if="loading" class="loading">Loading purchases...</div>
      <div v-else-if="error" class="error">{{ error }}</div>
      <div v-else>
        <div v-if="purchases.length > 0" class="poster-list">
          <div v-for="poster in purchases" :key="poster.id" class="poster-card">
            <img :src="poster.imageUrl" alt="Poster Image" class="poster-image" />
            <h3>{{ poster.title }}</h3>
            <p>{{ poster.description }}</p>
            <p class="price">{{ formatPrice(poster.price) }}</p>
          </div>
        </div>
        <div v-else class="no-purchases">No purchases yet.</div>
      </div>
    </div>
</template>
  
<script>
  export default {
    name: "PurchaseHistory",
    data() {
      return {
        purchases: [],
        loading: true,
        error: null,
      };
    },
    created() {
      this.fetchPurchaseHistory();
    },
    watch: {
    '$route.params.userId': 'fetchPurchaseHistory', // Watch for changes in userId in the URL
    },
    methods: {
      async fetchPurchaseHistory() {
        try {
          const userId = this.$route.params.userId; // Get the userId from the URL
          const response = await fetch("http://localhost:8080/users/"+userId+"/purchases"); // Adjust API endpoint as needed
          if (!response.ok) {
            throw new Error(`HTTP error! Status: ${response.status}`);
          }
          const data = await response.json();
          this.purchases = data.posters.map(poster => ({
            ...poster,
            imageUrl: `data:image/png;base64,${poster.imageData}`
          }));
        } catch (err) {
          this.error = err.message;
        } finally {
          this.loading = false;
        }
      },
      formatPrice(value) {
        return new Intl.NumberFormat("en-CA", {
          style: "currency",
          currency: "CAD",
          minimumFractionDigits: 2,
        }).format(value);
      },
    },
  };
</script>
  
<style scoped>
  .history-container {
    background: #ec5e5e;
    color: white;
    padding: 20px;
    text-align: center;
  }
  .loading, .error, .no-purchases {
    font-size: 18px;
    margin-top: 20px;
  }
  .poster-list {
    display: flex;
    flex-wrap: wrap;
    justify-content: center;
    gap: 20px;
    margin-top: 20px;
  }
  .poster-card {
    background: #8a4cfd;
    border-radius: 8px;
    padding: 15px;
    width: 280px;
    text-align: center;
    box-shadow: 2px 2px 10px rgba(255, 255, 255, 0.1);
  }
  .poster-card h3 {
  color: #010101; /* Change color of poster title */
  }

  .poster-card p {
  color: #000000; /* Change color of description text */
  }
  .poster-image {
    width: 100%;
    height: auto;
    border-radius: 5px;
   }
  .price {
    font-weight: bold;
    color: #0e100e;
  }
</style>
  