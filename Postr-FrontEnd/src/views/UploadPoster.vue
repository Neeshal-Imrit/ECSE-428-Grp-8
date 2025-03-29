<template>
  <div class="upload-poster-page">
    <h1 class="page-title">Upload Poster</h1>

    <div class="upload-poster-container">
      <!-- Left Column: Image Upload -->
      <div class="upload-area">
        <div class="image-placeholder" @click="triggerFileInput">
          <!-- Hidden file input -->
          <input
            type="file"
            ref="fileInput"
            @change="handleFileUpload"
            accept="image/*"
          />
          <template v-if="previewImage">
            <img :src="previewImage" alt="preview" class="preview-image" />
          </template>
          <template v-else>
            <p>Add picture</p>
          </template>
        </div>
      </div>

      <div class="form-area">
        <form @submit.prevent="handleSubmit" class="poster-form">
          <div class="form-group">
            <label for="title">Title</label>
            <input
              id="title"
              v-model="title"
              type="text"
              placeholder="Enter poster title"
              required
            />
          </div>

          <div class="form-group">
            <label for="description">Description</label>
            <textarea
              id="description"
              v-model="description"
              placeholder="Enter poster description"
              rows="5"
              required
            ></textarea>
          </div>

          <div class="form-group">
            <label for="price">Price</label>
            <input
              id="price"
              v-model="price"
              type="number"
              min="0"
              step="0.01"
              placeholder="0.00"
              required
            />
          </div>

          <button type="submit" class="post-button">Post</button>
        </form>
      </div>
    </div>

    <FooterComponent />
  </div>
</template>

<script>
import axios from 'axios';
import FooterComponent from '@/components/Footer.vue';

export default {
  name: 'UploadPoster',
  components: { FooterComponent },
  data() {
    return {
      title: '',
      description: '',
      price: '',
      previewImage: null,
      imageFile: null,
    };
  },
  methods: {
    triggerFileInput() {
      this.$refs.fileInput.click();
    },
    handleFileUpload(event) {
      const file = event.target.files[0];
      if (!file) return;

      this.imageFile = file;

      const reader = new FileReader();
      reader.onload = (e) => {
        this.previewImage = e.target.result;
      };
      reader.readAsDataURL(file);
    },
    handleSubmit() {
      let base64Data = null;
      if (this.previewImage) {
        base64Data = this.previewImage.split(',')[1];
      }

      const requestBody = {
        title: this.title,
        description: this.description,
        price: parseFloat(this.price),
        imageData: base64Data,
        userEmail: 'john.doe@example.com'
      };

      axios.post('http://localhost:8080/posters', requestBody)
        .then(response => {
          console.log('Poster created successfully:', response.data);
          alert('Poster uploaded!');
          this.title = '';
          this.description = '';
          this.price = '';
          this.previewImage = null;
          this.imageFile = null;
        })
        .catch(error => {
          console.error('Error uploading poster:', error);
          alert('Error uploading poster. Please try again.');
        });
    },
  },
};
</script>

<style scoped>
.upload-poster-page {
  min-height: 100vh;
  padding-top: 80px;
  box-sizing: border-box;
  display: flex;
  flex-direction: column;
}

.page-title {
  text-align: center;
  margin-bottom: 1.5rem;
  font-size: 1.8rem;
}

.upload-poster-container {
  display: flex;
  flex-direction: row;
  gap: 2rem;
  margin: 0 auto;
  max-width: 1000px;
  width: 100%;
  padding: 0 2rem 2rem;
  box-sizing: border-box;
}

.upload-area {
  flex: 1;
  display: flex;
  flex-direction: column;
  align-items: center;
}

.image-placeholder {
  width: 100%;
  max-width: 400px;
  height: 300px;
  border: 2px dashed #ccc;
  border-radius: 8px;
  display: flex;
  justify-content: center;
  align-items: center;
  cursor: pointer;
  text-align: center;
  overflow: hidden;
}

.image-placeholder input[type='file'] {
  display: none;
}

.image-placeholder p {
  font-size: 1rem;
  color: #555;
}

.preview-image {
  width: 100%;
  height: 100%;
  object-fit: cover;
  border-radius: 8px;
}

.form-area {
  flex: 1;
}

.poster-form {
  display: flex;
  flex-direction: column;
}

.form-group {
  margin-bottom: 1rem;
}

.form-group label {
  font-weight: bold;
  margin-bottom: 0.5rem;
  display: inline-block;
}

.form-group input,
.form-group textarea {
  width: 100%;
  padding: 0.75rem;
  border: 1px solid #ccc;
  border-radius: 4px;
  box-sizing: border-box;
}

.post-button {
  background-color: #5b9bd5;
  color: #fff;
  padding: 0.75rem 1.5rem;
  font-size: 1rem;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  width: fit-content;
  margin-top: 1rem;
}

.post-button:hover {
  opacity: 0.9;
}
</style>
