import { ref } from 'vue';

const isLoggedIn = ref(localStorage.getItem('userEmail') !== null);

const login = (email) => {
  localStorage.setItem('userEmail', email);
  isLoggedIn.value = true;
};

const logout = () => {
  localStorage.removeItem('userEmail');
  isLoggedIn.value = false;
};

export { isLoggedIn, login, logout };