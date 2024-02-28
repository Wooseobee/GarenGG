<template>
  <div>

    <body class="index-page" data-bs-spy="scroll" data-bs-target="#navmenu">
      <Header />
      <RouterView />
    </body>

  </div>
</template>

<script setup>
import { RouterView } from 'vue-router';
import Header from "@/components/common/Header.vue"

</script>

<style>
:root {
  --default-font: "Open Sans", system-ui, -apple-system, "Segoe UI", Roboto, "Helvetica Neue", Arial, "Noto Sans", "Liberation Sans", sans-serif, "Apple Color Emoji", "Segoe UI Emoji", "Segoe UI Symbol", "Noto Color Emoji";
  --heading-font: "Montserrat", sans-serif;
  --nav-font: "Lato", sans-serif;
}

/* Global Colors */
:root {
  --background-color: #ffffff;
  --background-color-rgb: 255, 255, 255;
  --default-color: #212529;
  --default-color-rgb: 33, 37, 41;
  --accent-color: #007BFF;
  --accent-color-rgb: 232, 69, 69;
  --heading-color: #32353a;
  --heading-color-rgb: 50, 53, 58;
  --contrast-color: #ffffff;
  --contrast-color-rgb: 255, 255, 255;
}

/* Nav Menu Colors */
:root {
  --nav-color: #3a3939;
  --nav-hover-color: #e84545;
  --nav-dropdown-color: #3a3939;
  --nav-dropdown-hover-color: #e84545;
  --nav-dropdown-background-color: #ffffff;
}

/* Smooth scroll */
:root {
  scroll-behavior: smooth;
}

a, .router-link-exact-active, .router-link-active {
  text-decoration: none;
}

</style>