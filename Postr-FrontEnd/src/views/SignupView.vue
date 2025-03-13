<template>
    <div class="center-content">
        <h1>Sign Up Page</h1>
        <p>Welcome to Postr!</p>
        <form @submit.prevent="submitForm">
            <div>
                <label for="email">Email:</label>
                <input type="email" id="email" v-model="email" required>
            </div>
            <div>
                <label for="password">Password:</label>
                <input type="password" id="password" v-model="password" required>
            </div>
            <button type="submit">Sign Up</button>
        </form>
    </div>
</template>

<script>
export default {
  name: 'SignupView',
  data() {
    return {
      email: '',
      password: ''
    };
  },
  methods: {
    submitForm() {
      // Handle form submission. This is a placeholder. Can remove when you connect to backend.
      // Not supposed to log in final version
      console.log('Email:', this.email);
      console.log('Password:', this.password);
    }
  }
}
</script>

<style scoped>
.center-content {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  height: 100vh;
  text-align: center;
}

form {
  display: flex;
  flex-direction: column;
  gap: 1rem;
}

label {
  margin-bottom: 0.5rem;
}

input {
  padding: 0.5rem;
  font-size: 1rem;
}

button {
  padding: 0.5rem 1rem;
  font-size: 1rem;
  cursor: pointer;
}
</style>