<template>
  <div class="center-content">
    <!-- left side - form -->
    <div class="form-container">
      <h1>Sign in Page</h1>
      <p class="subtitle">Welcome back to Postr!</p>
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
          <button type="submit">Sign In</button>
        </div>
      </form>
      <p class="signup-link">
        Don't have an account?
        <router-link to="/signup">Sign up here</router-link>
      </p>
    </div>

    <!-- right side - image -->
    <div class="image-container">
      <img src="@/assets/signin-background.jpg" alt="Sign In Image" />
    </div>
  </div>
</template>

<script>
export default {
  name: "SigninView",
  data() {
    return {
      email: "",
      password: "",
    };
  },
  methods: {
    submitForm() {
      // Handle form submission. This is a placeholder. Can remove when you connect to backend.
      // Not supposed to log in final version
      console.log("Email:", this.email);
      console.log("Password:", this.password);
    },
  },
};
</script>

<style scoped>
.form-container {
  flex: 2;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  height: 100vh;
  padding: 2rem;
  background-color: white;
  text-align: center;
}

form {
  display: flex;
  flex-direction: column;
  gap: 1rem;
  width: 100%;
  max-width: 400px;
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

.signup-link {
  margin-top: 10px;
  font-size: 10px;
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
