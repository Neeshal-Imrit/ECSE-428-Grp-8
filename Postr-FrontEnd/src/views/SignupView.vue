<template>
  <div class="center-content">
    <!-- left side - image -->
    <div class="image-container">
      <img src="@/assets/signup-background.png" alt="Sign Up Image" />
    </div>

    <!-- right side - form -->
    <div class="form-container">
      <h1>Create an account</h1>
      <p class="subtitle">Welcome to Postr!</p>
      <form @submit.prevent="submitForm">
        <div class="input-group">
          <label for="email">Email</label>
          <input type="email" id="email" v-model="email" required />
        </div>
        <div class="input-group">
          <label for="password">Password</label>
          <input type="password" id="password" v-model="password" required />
        </div>
        <div>
          <button type="submit">Sign Up</button>
        </div>
      </form>
    </div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  name: "SignupView",
  data() {
    return {
      email: "",
      password: "",
    };
  },
  methods: {
    async submitForm() {
      try {
        const response = await axios.post(`http://localhost:8080/users`, {
          email: this.email,
          password: this.password
        });
        console.log('User created successfully:', response.data);
       
        this.email = "";
        this.password = "";
        this.$router.push('/signin');
        
      } catch (error) {
        console.error('Error creating user:', error.response.data);
      }
    },
  },
};
</script>

<style scoped>
html,
body {
  overflow: hidden;
  margin: 0;
  padding: 0;
  width: 100%;
  height: 100%;
  box-sizing: border-box;
}

.center-content {
  display: flex;
  flex: 1 1 auto;
  width: 100vw;
  height: 100vh;
  overflow: hidden;
}

.subtitle {
  margin-bottom: 30px;
}

.image-container {
  flex: 1;
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: #f5f5f5;
}

.image-container img {
  width: 100%;
  height: 100vh;
  object-fit: cover;
}

.form-container {
  flex: 1;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  padding: 2rem;
  background-color: white;
  text-align: center;
}

form {
  display: flex;
  flex-direction: column;
  gap: 1rem;
  width: 100%;
  max-width: 300px;
}

label {
  margin-bottom: 0.5rem;
  text-align: left;
}

input {
  width: 100%;
  padding: 0.5rem;
  font-size: 1rem;
  border: 1px solid #ced4da;
  border-radius: 8px;
  transition: box-shadow 0.2s ease-in-out, border-color 0.2s ease-in-out;
  outline: none;
}

input:hover,
input:focus {
  box-shadow: 0 0 5px #a2a6aa;
  border-color: #a2a6aa;
}

.input-group {
  display: flex;
  flex-direction: column;
  text-align: left;
}

button {
  padding: 0.5rem 1rem;
  font-family: "Poppins", sans-serif;
  font-size: 0.5Srem;
  cursor: pointer;
  background-color: #50abdd;
  color: white;
  border: none;
  border-radius: 3px;
  margin-top: 30px;
  width: 40%;
  height: 40px;
}

button:hover {
  background-color: #30a4e2;
}

button:active {
  background-color: #2088c9;
}
</style>