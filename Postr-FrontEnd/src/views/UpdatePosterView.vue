<template>
  <div class="update-poster-page">
    <h1 class="page-title">Update Poster</h1>

    <div class="update-poster-container">
      <!-- Left Column: Image Update -->
      <div class="update-area">
        <div class="image-placeholder" @click="triggerFileInput">
          <input
            type="file"
            ref="fileInput"
            @change="handleFileUpdate"
            accept="image/*"
          />
          <template v-if="imageUrl">
            <img :src="imageUrl" alt="preview" class="preview-image" />
          </template>
          <template v-else>
            <p>Add picture</p>
          </template>
        </div>
      </div>

      <!-- Right Column: Poster Form -->
      <div class="form-area">
        <form @submit.prevent="handleSubmit" class="poster-form">
          <div class="form-group">
            <label for="title">Title</label>
            <input
              id="title"
              class="text-container"
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
              class="text-container"
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
              class="text-container"
              v-model="price"
              type="number"
              min="0"
              step="0.01"
              placeholder="0.00"
              required
            />
          </div>

          <button type="submit" class="update-button">Update</button>
        </form>
      </div>
    </div>

    <FooterComponent />
  </div>
</template>

<script>
import axios from "axios";
import FooterComponent from "@/components/Footer.vue";

export default {
  name: "UpdatePoster",
  components: { FooterComponent },
  data() {
    return {
      title: "Sun & Blue",
      price: 13.99,
      description:
        "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Ut elit tellus, luctus nec ullamcorper mattis.",
      imageUrl: "src/assets/POSTER-sun&blue.jpg",
    };
  },
  methods: {
    triggerFileInput() {
      this.$refs.fileInput.click();
    },
    handleFileUpdate(event) {
      const file = event.target.files[0];
      if (!file) return;

      this.imageFile = file;

      const reader = new FileReader();
      reader.onload = (e) => {
        this.imageUrl = e.target.result;
      };
      reader.readAsDataURL(file);
    },
    handleSubmit() {
      let base64Data = null;
      if (this.imageUrl) {
        base64Data = this.imageUrl.split(",")[1];
      }

      const requestBody = {
        title: this.title,
        description: this.description,
        price: parseFloat(this.price),
        imageData: base64Data,
        userEmail: "john.doe@example.com",
      };

      axios
        .put(`http://localhost:8080/posters/${poster.id}`, requestBody)
        .then((response) => {
          console.log("Poster updated successfully:", response.data);
          alert("Poster updated!");
          this.title = "";
          this.description = "";
          this.price = "";
          this.previewImage = null;
          this.imageFile = null;
        })
        .catch((error) => {
          console.error("Error updating poster:", error);
          alert("Error updating poster. Please try again.");
        });
    },
  },
};
</script>

<style scoped>
.update-poster-page {
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

.update-poster-container {
  display: flex;
  flex-direction: row;
  gap: 2rem;
  margin: 0 auto;
  max-width: 1000px;
  width: 100%;
  padding: 0 2rem 2rem;
  box-sizing: border-box;
}

.update-area {
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

.image-placeholder input[type="file"] {
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

.text-container {
  font-family: "Poppins", sans-serif;
}

.update-button {
  background-color: #5b9bd5;
  color: #fff;
  padding: 0.75rem 1.5rem;
  font-size: 1rem;
  font-family: "Poppins", sans-serif;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  width: fit-content;
  margin-top: 1rem;
}

.update-button:hover {
  opacity: 0.9;
}
</style>
