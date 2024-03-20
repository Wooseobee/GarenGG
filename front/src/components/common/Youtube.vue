<template>
  <div>
    <div v-if="searchResults.length">
      <div v-for="video in searchResults" :key="video.id">
        <iframe
          width="280"
          height="157"
          :src="'https://www.youtube.com/embed/' + video.id"
          frameborder="0"
          allowfullscreen
        ></iframe>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from "vue";
import axios from "axios";
const props = defineProps({
  searchResults: {
    type: Array,
    required: true,
  },
});
</script>

<style scoped></style>
