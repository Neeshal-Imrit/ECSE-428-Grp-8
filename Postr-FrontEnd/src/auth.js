import { ref } from 'vue';

const userEmail = ref(localStorage.getItem('userEmail') || null);
const userId = ref(localStorage.getItem('userId') || null);
const isLoggedIn = ref(userEmail.value !== null);

const login = (email, id) => {
  localStorage.setItem('userEmail', email);
  localStorage.setItem('userId', id);
  userEmail.value = email;
  userId.value = id;
  isLoggedIn.value = true;
};

const logout = () => {
  localStorage.removeItem('userEmail');
  localStorage.removeItem('userId');
  userEmail.value = null;
  userId.value = null;
  isLoggedIn.value = false;
};

export { userEmail, isLoggedIn, login, logout };
