<template>
  <div>
    <div v-if="searchResults.length" class="row">
      <div v-for="video in searchResults" :key="video.id" class="col-md-4">
        <iframe
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

<style scoped>
iframe {
  max-width: 440px;
  height: 235px;
}
</style>
