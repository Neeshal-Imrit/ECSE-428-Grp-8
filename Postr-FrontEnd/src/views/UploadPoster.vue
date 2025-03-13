<template>
  <div class="upload-poster-page">
    <div class="upload-poster-container">
      <!-- Left side: Image upload area -->
      <div class="upload-area">
        <div class="image-placeholder" @click="triggerFileInput">
          <p>Add picture</p>
          <input 
            type="file"
            ref="fileInput"
            @change="handleFileUpload"
            accept="image/*"
          />
        </div>
        <div v-if="previewImage" class="preview-container">
          <img :src="previewImage" alt="preview" />
        </div>
      </div>

      <!-- Right side: form fields -->
      <div class="form-area">
        <h1>Upload poster</h1>
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

    <!-- Use the separate Footer component here -->
    <FooterComponent />
  </div>
</template>

<script>
import FooterComponent from "@/components/Footer.vue"; 
// Adjust the path above to match your folder structure

export default {
  name: "UploadPoster",
  components: {
    FooterComponent,
  },
  data() {
    return {
      title: "",
      description: "",
      price: "",
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
      const formData = new FormData();
      formData.append("title", this.title);
      formData.append("description", this.description);
      formData.append("price", this.price);
      if (this.imageFile) {
        formData.append("image", this.imageFile);
      }

      // e.g. axios.post("/api/posters", formData)...
      // Clear form after submit (optional)
      this.title = "";
      this.description = "";
      this.price = "";
      this.imageFile = null;
      this.previewImage = null;
      alert("Poster submitted!");
    },
  },
};
</script>

<style scoped>
.upload-poster-page {
  width: 100%;
  /* min-height: 100vh;
  margin: 0;
  padding-top: 80px;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  box-sizing: border-box; */
}

/* Container: full width */
.upload-poster-container {
  /* display: flex;
  flex: 1;
  width: 100%;
  margin: 0;
  padding: 1rem;
  box-sizing: border-box;
  gap: 2rem; */
  width: 100%;
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
}

.image-placeholder input[type="file"] {
  display: none;
}

.image-placeholder p {
  font-size: 1rem;
  color: #555;
}

.preview-container {
  margin-top: 1rem;
  width: 100%;
  max-width: 400px;
}

.preview-container img {
  width: 100%;
  height: auto;
  object-fit: cover;
}

.form-area {
  flex: 1;
}

.form-area h1 {
  margin-bottom: 1rem;
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
