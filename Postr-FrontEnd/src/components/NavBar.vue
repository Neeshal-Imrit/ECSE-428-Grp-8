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
      <li v-if="!isLoggedIn"><router-link to="/signup">Sign Up</router-link></li>
      <li v-if="!isLoggedIn"><router-link to="/signin">Sign In</router-link></li>
      <li v-if="isLoggedIn"><button class="sign-out-button" @click="signOut">Sign Out</button></li>
    </ul>

    <!-- Actions -->
    <div class="navbar__actions">
      <router-link to="/favorites" class="navbar__icon">
        <i class="icon-heart"></i>
      </router-link>
    </div>
  </nav>
</template>

<script>
import { isLoggedIn, logout } from '@/auth';
import { useRouter } from 'vue-router';

export default {
  name: 'NavigationBar',
  setup() {
    const router = useRouter();

    const signOut = () => {
      logout();
      router.push('/signin');
    };

    return {
      isLoggedIn,
      signOut,
    };
  },
};
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

button {
  background: none;
  border: none;
  color: #333;
  cursor: pointer;
  font-size: 1rem;
}

button:hover {
  text-decoration: underline;
}

.sign-out-button {
  color: red;
}

.sign-out-button:hover {
  text-decoration: underline;
}
</style>