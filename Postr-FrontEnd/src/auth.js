import { ref } from 'vue';

const userEmail = ref(localStorage.getItem('userEmail') || null);
const isLoggedIn = ref(userEmail.value !== null);

const login = (email) => {
  localStorage.setItem('userEmail', email);
  userEmail.value = email;
  isLoggedIn.value = true;
};

const logout = () => {
  localStorage.removeItem('userEmail');
  userEmail.value = null;
  isLoggedIn.value = false;
};

export { userEmail, isLoggedIn, login, logout };
