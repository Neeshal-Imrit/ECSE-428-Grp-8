<template>
  <footer class="footer">
    <div class="footer-content">
      <h3>Postr.</h3>
      <p>400 University Drive Suite 200 Coral Gables,</p>
      <p>FL 33134 USA</p>
      <p>2025 Postr. All rights reserved</p>
    </div>
  </footer>
</template>

<script>
export default {
  name: "FooterComponent",
};
</script>

<style scoped>
.footer {
  width: 100vw;
  background-color: #fff;
  border-top: 1px solid #ddd;
  padding: 2rem 1rem;
  text-align: center;
  box-sizing: border-box;
}

.footer-content h3 {
  margin-bottom: 0.5rem;
  font-size: 1.25rem;
}
</style>
