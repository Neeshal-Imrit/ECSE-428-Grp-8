<template>
  <div>
    <!-- Your always-on-top nav bar -->
    <NavBar />

    <!-- This is where different pages (Home, Shop, etc.) will appear -->
    
    <router-view />
  </div>
</template>

<script>
import NavBar from './components/NavBar.vue'

export default {
  name: 'App',
  components: {
    NavBar
  }
}
</script>
