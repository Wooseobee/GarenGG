<template>
  <div>
    <body
      class="index-page background"
      data-bs-spy="scroll"
      data-bs-target="#navmenu"
    >
      <RouterView />
    </body>
  </div>
</template>

<script setup>
import { RouterView } from "vue-router";
</script>

<style>
@font-face {
  font-family: "BeaufortforLOL-Bold";
  src: url("@/fonts/BeaufortforLOL-Bold.otf") format("opentype"),
    url("@/fonts/BeaufortforLOL-Bold.ttf") format("truetype"),
    url("@/fonts/BeaufortforLOL-BoldItalic.otf") format("opentype"),
    url("@/fonts/BeaufortforLOL-Heavy.otf") format("opentype"),
    url("@/fonts/BeaufortforLOL-HeavyItalic.otf") format("opentype"),
    url("@/fonts/BeaufortforLOL-Italic.otf") format("opentype"),
    url("@/fonts/BeaufortforLOL-Italic(1).otf") format("opentype"),
    url("@/fonts/BeaufortforLOL-Light.otf") format("opentype"),
    url("@/fonts/BeaufortforLOL-LightItalic.otf") format("opentype"),
    url("@/fonts/BeaufortforLOL-LightItalic(1).otf") format("opentype"),
    url("@/fonts/BeaufortforLOL-Medium.otf") format("opentype"),
    url("@/fonts/BeaufortforLOL-MediumItalic.otf") format("opentype"),
    url("@/fonts/BeaufortforLOL-Regular.otf") format("opentype");
  font-weight: normal;
  font-style: normal;
}

body {
  color: #ffffff;
  font-family: "BeaufortforLOL-Bold", sans-serif;
  background-color: #091428;
}

:root {
  --default-font: "Open Sans", system-ui, -apple-system, "Segoe UI", Roboto,
    "Helvetica Neue", Arial, "Noto Sans", "Liberation Sans", sans-serif,
    "Apple Color Emoji", "Segoe UI Emoji", "Segoe UI Symbol", "Noto Color Emoji";
  --heading-font: "Montserrat", sans-serif;
  --nav-font: "Lato", sans-serif;
}

/* Global Colors */
:root {
  --background-color: #091428;
  --background-color-rgb: 9, 20, 40;
  --default-color: #212529;
  --default-color-rgb: 200, 155, 60;
  --accent-color: #007bff;
  --accent-color-rgb: 232, 69, 69;
  --heading-color: #32353a;
  --heading-color-rgb: 50, 53, 58;
  --contrast-color: #091428;
  --contrast-color-rgb: 9, 20, 40;
}

/* Nav Menu Colors */
:root {
  --nav-color: #3a3939;
  --nav-hover-color: #e84545;
  --nav-dropdown-color: #3a3939;
  --nav-dropdown-hover-color: #e84545;
  --nav-dropdown-background-color: #091428;
}

/* Smooth scroll */
:root {
  scroll-behavior: smooth;
}

a,
.router-link-exact-active,
.router-link-active {
  text-decoration: none;
}
</style>
