<template>
  <div class="upload-poster-page">
    <!-- Two-column content wrapper -->
    <div class="upload-poster-container">

      <!-- Left half: Image upload area -->
      <div class="upload-area">
        <div class="image-placeholder" @click="triggerFileInput">
          <p>Add picture</p>
          <!-- Hidden file input -->
          <input
            type="file"
            ref="fileInput"
            @change="handleFileUpload"
            accept="image/*"
          />
        </div>
        <div v-if="previewImage" class="preview-container">
          <img :src="previewImage" alt="Preview" />
        </div>
      </div>

      <!-- Right half: Form fields -->
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

    <!-- Separate footer component -->
    <FooterComponent />
  </div>
</template>

<script>
import FooterComponent from "@/components/FooterComponent.vue"; 
// Adjust path if needed

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
      // Programmatically open the hidden file input
      this.$refs.fileInput.click();
    },
    handleFileUpload(event) {
      const file = event.target.files[0];
      if (!file) return;

      this.imageFile = file;

      // Create a preview URL with FileReader
      const reader = new FileReader();
      reader.onload = (e) => {
        this.previewImage = e.target.result;
      };
      reader.readAsDataURL(file);
    },
    handleSubmit() {
      // Example: Gather form data
      const formData = new FormData();
      formData.append("title", this.title);
      formData.append("description", this.description);
      formData.append("price", this.price);
      if (this.imageFile) {
        formData.append("image", this.imageFile);
      }

      // Example POST request
      // axios.post("/api/posters", formData)
      //   .then(response => { ... })
      //   .catch(error => { ... });

      // Clear form
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
/* Add top padding to stay below any fixed navbar. 
   Adjust if your navbar is taller/shorter than 80px. */
.upload-poster-page {
  width: 100%;
  min-height: 100vh;
  margin: 0;
  padding-top: 80px;
  box-sizing: border-box;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
}

/* Creates a 2-column layout spanning full width. */
.upload-poster-container {
  display: flex;
  flex: 1;
  width: 100%;
  margin: 0;
  padding: 1rem;
  box-sizing: border-box;
}

/* Left column */
.upload-area {
  width: 50%;
  display: flex;
  flex-direction: column;
  align-items: center;
  box-sizing: border-box;
}

/* Right column */
.form-area {
  width: 50%;
  padding: 0 1rem; /* optional spacing */
  box-sizing: border-box;
}

.image-placeholder {
  width: 100%;
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
}

.preview-container img {
  width: 100%;
  height: auto;
  object-fit: cover;
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
