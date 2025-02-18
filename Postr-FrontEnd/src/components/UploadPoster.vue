<template>
    <div>
      <h1>Upload a Poster</h1>
  
      <form @submit.prevent="handleSubmit">
        <div>
          <label for="title">Title:</label>
          <input id="title" v-model="title" type="text" required />
        </div>
        <div>
          <label for="description">Description:</label>
          <textarea id="description" v-model="description" required></textarea>
        </div>
        <div>
          <label for="price">Price:</label>
          <input id="price" type="number" v-model.number="price" required />
        </div>
        <div>
          <label for="userEmail">User Email:</label>
          <input id="userEmail" v-model="userEmail" type="email" required />
        </div>
        <div>
          <label for="image">Image (File):</label>
          <input id="image" type="file" accept="image/*" @change="onFileChange" />
        </div>
        <button type="submit" :disabled="loading">Upload</button>
        <div v-if="error" class="error">{{ error }}</div>
      </form>
    </div>
  </template>
  
  <script>
  import { ref } from 'vue';
  import { usePosterStore } from '@/stores/posterStore';
  
  export default {
    name: 'UploadPoster',
    setup() {
      const posterStore = usePosterStore();

      const title = ref('');
      const description = ref('');
      const price = ref(0);
      const userEmail = ref('');
      const imageData = ref(null);
      const loading = ref(false);
      const error = ref(null);
  
      const onFileChange = (event) => {
        const file = event.target.files[0];
        if (!file) return;
  
        const reader = new FileReader();
        reader.onload = (e) => {
          let result = e.target.result;
          if (result.indexOf(',') !== -1) {
            result = result.split(',')[1];
          }
          imageData.value = result;
        };
        reader.readAsDataURL(file);
      };
  
      const handleSubmit = async () => {
        if (!title.value || !description.value || !userEmail.value || !imageData.value) {
          error.value = 'Please fill in all required fields.';
          return;
        }
        error.value = null;
        loading.value = true;
  
        const posterRequest = {
          title: title.value,
          description: description.value,
          price: price.value,
          imageData: imageData.value,
          userEmail: userEmail.value,
        };
  
        try {
          await posterStore.uploadPoster(posterRequest);
          title.value = '';
          description.value = '';
          price.value = 0;
          userEmail.value = '';
          imageData.value = null;
        } catch (err) {
          error.value =
            (err.response && err.response.data && err.response.data.message) ||
            err.message ||
            'Upload failed';
        } finally {
          loading.value = false;
        }
      };
  
      return {
        title,
        description,
        price,
        userEmail,
        imageData,
        loading,
        error,
        onFileChange,
        handleSubmit,
      };
    },
  };
  </script>
  
  <style scoped>
  .error {
    color: red;
    margin-top: 10px;
  }
  form > div {
    margin-bottom: 15px;
  }
  </style>
  