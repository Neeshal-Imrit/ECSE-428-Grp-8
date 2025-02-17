<template>
  <div class="background">
    <div class="container">
      <h5 class="subtitle">New Arrival</h5>
      <p class="title">Discover Our</p>
      <p class="title">New Collection</p>

      <p class="text">Discover What’s New – Fresh Arrivals, Just for You!</p>
      <button class="button" @click="handleClick">SHOP NOW</button>
    </div>
  </div>
</template>

<script>
export default {
  name: "NewArrival",
  methods: {
    handleClick() {
      this.$router.push({ name: "shop" }); // Redirect to ShopView page
    },
  },
};
</script>

<style scoped>
/* Ensure no margin on body and html */
html,
body {
  margin: 0;
  padding: 0;
  overflow-x: hidden; /* Prevent horizontal scrolling */
  overflow-y: auto; /* Allow vertical scrolling */
  width: 100%; /* Ensure full width */
  height: 100%; /* Ensure full height */
}

/* Full-width background */
.background {
  width: 100vw;
  height: calc(80vh - 60px);
  background-image: url("@/assets/posters-on-wall.jpg");
  background-size: cover;
  background-position: center;
  background-attachment: scroll;
  display: flex;
  justify-content: flex-end;
  align-items: center;
  padding-right: 50px;
  margin-top: 60px;
}

/* The container in front of the background */
.container {
  width: 30%;
  height: 80%;
  padding: 50px 30px 30px 30px;
  background-color: #fff3e3;
  border-radius: 8px;
  box-shadow: 2px 2px 10px rgba(0, 0, 0, 0.2);
  text-align: left;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
}

/* Text styling */
.text {
  font-size: 15px;
  margin-top: 10px;
  margin-bottom: 10px;
  color: #333;
}

.title {
  font-size: 35px;
  font-weight: 900;
  color: #b88e2f;
  line-height: 0.8;
}

.subtitle {
  font-weight: bold;
}

/* Button styling */
.button {
  background-color: #b88e2f;
  color: white;
  border: none;
  padding: 15px 40px;
  border-radius: 2px;
  cursor: pointer;
  font-size: 10px;
  font-weight: bold;
  align-self: flex-start;
}

.button:hover {
  background-color: #876922;
}
</style>
