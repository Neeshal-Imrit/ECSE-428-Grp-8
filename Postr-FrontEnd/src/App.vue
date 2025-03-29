<template>
  <div>
    <NavBar />


    <router-view />
  </div>
</template>

<script>
import NavBar from "./components/NavBar.vue";

export default {
  name: "App",
  components: {
    NavBar,
  },
};
</script>

<style>
body {
  background-color: #ffffff;
  color: #333;
  margin: 0;
  font-family: "Poppins", sans-serif;
  overflow-x: hidden;
  width: 100%;
  box-sizing: border-box;
}
</style>
