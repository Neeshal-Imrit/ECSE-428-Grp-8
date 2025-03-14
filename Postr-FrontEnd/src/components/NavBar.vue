<template>
  <nav class="navbar">
    <!-- Brand / Logo -->
    <div class="navbar__brand">
      <router-link to="/" class="navbar__logo">
        postr.
      </router-link>
    </div>

    <!-- Navigation Links -->
    <ul class="navbar__links">
      <li><router-link to="/">Home</router-link></li>
      <li><router-link to="/shop">Shop</router-link></li>
      <li><router-link to="/my-posters">My posters</router-link></li>
      

    </ul>

    <!-- Actions -->
    <div class="navbar__actions">
      <router-link to="/favorites" class="navbar__icon">
        <i class="icon-heart"></i>
      </router-link>
      <button class="navbar__signout" @click="handleSignOut">Sign out</button>
    </div>
  </nav>
</template>

import { state } from '../stores/state';

<script>
export default {
  setup() {
    return { state };
  },
  name: 'NavBar',
  methods: {
    handleSignOut() {
      // Your sign-out logic
      alert('Signed out!');
    }
  }
}
</script>

<style scoped>
.navbar {
  position: fixed; 
  top: 0; 
  left: 0; 
  right: 0; 
  z-index: 9999;
  height: 60px;
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0 1rem;
  background-color: #fff;
  border-bottom: 1px solid #eaeaea;
}

.navbar__brand .navbar__logo {
  font-size: 1.5rem;
  font-weight: bold;
  text-decoration: none;
  color: #000;
}

.navbar__links {
  list-style: none;
  display: flex;
  gap: 1rem;
  margin: 0;
  padding: 0;
}
.navbar__links li a {
  text-decoration: none;
  color: #333;
  font-weight: 500;
}

.navbar__actions {
  display: flex;
  align-items: center;
  gap: 1rem;
}
</style>
