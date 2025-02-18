import { defineStore } from 'pinia';
import axios from 'axios';

export const usePosterStore = defineStore('poster', {
  state: () => ({
    posters: [],
    singlePoster: null,
    loading: false,
    error: null
  }),

  actions: {
    async fetchAllPosters() {
      this.loading = true;
      this.error = null;
      try {
        const response = await axios.get('http://localhost:8080/posters');
        this.posters = response.data;
      } catch (err) {
        this.error = err.message;
      } finally {
        this.loading = false;
      }
    },

    async fetchPosterById(id) {
      this.loading = true;
      this.error = null;
      try {
        const response = await axios.get(`http://localhost:8080/posters/id/${id}`);
        this.singlePoster = response.data;
      } catch (err) {
        this.error = err.message;
      } finally {
        this.loading = false;
      }
    },

    async uploadPoster(posterRequest) {
      this.loading = true;
      this.error = null;
      try {
        const response = await axios.post('http://localhost:8080/posters', posterRequest);
        this.posters.push(response.data);
      } catch (err) {
        this.error = err.message;
      } finally {
        this.loading = false;
      }
    }
  }
});