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
import imageOne from '@/assets/POSTER-be-brave.jpg';
import imageTwo from '@/assets/POSTER-sun&blue.jpg';
import imageThree from '@/assets/POSTER-holland-market.jpg';
import imageFour from '@/assets/claudio-schwarz.jpg';
import imageFive from '@/assets/jazmin-quaynor.jpg';
import imageSix from '@/assets/mcgill-library.jpg';

export default {
  name: 'shop',
  data() {
    return {
      posters: [],
      loading: true,
      error: null,
      // Dummy posters data
      dummyPosters: [
        {
          id: 1,
          url: imageOne,
          title: "Be Brave",
          description: "A beautiful vintage poster.",
          price: 19.99
        },
        {
          id: 2,
          url: imageTwo,
          title: "Sun & Blue",
          description: "A modern art poster with vibrant colors.",
          price: 29.99
        },
        {
          id: 3,
          url: imageThree,
          title: "Holland Market",
          description: "A scenic view of nature.",
          price: 15.99
        },
        {
          id: 4,
          url: imageFour,
          title: "Claudio Schwarz",
          description: "Privatsphare",
          price: 20.99
        },
        {
          id: 5,
          url: imageFive,
          title: "Jazmin Quaynor",
          description: "Poster history",
          price: 12.99
        },
        {
          id: 6,
          url: imageSix,
          title: "Mcgill Library",
          description: "A Mcgill Library poster",
          price: 5.99
        }
      ]
    } 
  },
  created() {
    // Uncomment the line below to fetch from backend
    // this.fetchPosters();
        // For dummy data, comment out the fetch call and use dummyPosters
    //this.posters = this.dummyPosters;
    this.loading = false;
  },
  methods: {
    async fetchPosters() {
      try {
        // Replace with your backend URL
        const response = await fetch('http://localhost:8000/posters')
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
    async buyNow(poster) {
      alert(`You have selected "${poster.title}" for ${this.formatPrice(poster.price)}.`);

      try {

        //get the user id with the email
        try {
          const email = localStorage.getItem("email");
          const response = await fetch(`http://localhost:8000/users/${email}`);

          const data = await response.json();
          console.log("User Data:", data);
          const userID = data.id;
          console.log("User ID:", userID);
        } catch (error) {
          console.error("Error fetching user data:", error.message);
        }
        const response = await fetch('http://localhost:8080//posters/buy/{posterId}')
        const responseBuyPoster = await fetch(`http://localhost:8080//users/${userID}/purchase/{posterId}`)



        if (!response.ok) {
          throw new Error(`HTTP error! Status: ${response.status}`)
        }
        this.posters = await response.json()
      } catch (err) {
        this.error = err.message
      } finally {
        this.loading = false
      }
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
