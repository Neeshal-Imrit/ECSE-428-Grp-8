<template>
  <div>
    <NavBar />

    <section class="poster-detail-container">
      <div class="detail-view" v-if="poster">
        <div class="poster-image">
          <img :src="poster.imageUrl" :alt="poster.title" />
        </div>
        <div class="poster-info">
          <h1 class="poster-title">{{ poster.title }}</h1>
          <p class="poster-price">${{ poster.price }}</p>
          <p class="poster-description">{{ poster.description }}</p>
          <div class="buttons-container">
            <button class="button buy-button">
              <span class="buy-text">Buy now</span>
            </button>
            <button
              @click="toggleLike"
              class="button flex items-center border px-4 py-2 rounded-lg hover:bg-gray-50"
            >
              <span v-if="liked" class="text-red-500">❤️</span>
              <span v-else class="text-gray-500">🤍</span>
              <span class="like-text">Like</span>
            </button>
          </div>
        </div>
      </div>
      <div v-else>
        <p>Loading poster details...</p>
      </div>
    </section>

    <FooterComponent />
  </div>
</template>

<script setup>
import { ref, onMounted } from "vue";
import { useRoute } from "vue-router";

import NavBar from "@/components/NavBar.vue";
import FooterComponent from "@/components/Footer.vue";

const poster = ref(null);
const liked = ref(false);
const route = useRoute();

onMounted(async () => {
  const { id } = route.params;

  try {
    const response = await fetch(`https://api.example.com/posters/${id}`);
    const data = await response.json();
  } catch (error) {
    console.error("Error fetching poster:", error);
  }
});

function toggleLike() {
  liked.value = !liked.value;
}
</script>

<style scoped>
.poster-detail-container {
  width: 100vw;
  padding-top: 140px;
  padding-bottom: 80px;
  display: flex;
  align-items: center;
  justify-content: center;
}

.detail-view {
  display: flex;
  min-width: 500px;
  width: 100%;
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: center;
  width: 100%;
  padding-left: 100px;
  padding-right: 100px;
}

.poster-image {
  display: flex;
  width: 250px;
  padding-right: 20px;
  justify-content: center;
  object-fit: cover;
}

img {
  width: 100%;
  border-radius: 5px;
  box-shadow: 0px 0px 10px rgba(172, 172, 172, 0.5);
}

.poster-title {
  font-weight: bold;
}

.poster-info {
  display: flex;
  flex-direction: column;
  justify-content: center;
  width: 500px;
  padding-left: 50px;
}

.poster-description {
  margin-top: 1rem;
  line-height: 1.5;
  margin-bottom: 2rem;
}

.buttons-container {
  display: flex;
  flex-direction: row;
  gap: 10px;
}

.button {
  cursor: pointer;
  display: flex;
  align-items: center;
  justify-content: center;
  border: 0.5px solid #d1d5db;
  background-color: rgb(255, 255, 255);
  border-radius: 100px;
  width: 75px;
  height: 40px;
  margin-right: 10px;
}

.button:hover {
  background-color: rgb(240, 240, 240);
}

.button:active {
  background-color: rgb(225, 225, 225);
}

.buy-button {
  background-color: #b88e2f;
  color: white;
  border: 0px;
  width: 85px;
}

.buy-button:hover {
  background-color: #9b7928;
}

.buy-button:active {
  background-color: #866823;
}

.buy-text {
  font-family: "Poppins", sans-serif;
}

.like-text {
  margin-left: 5px;
  font-family: "Poppins", sans-serif;
}
</style>
