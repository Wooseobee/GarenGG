<template>
  <div>
    <div v-if="searchResults.length" class="row">
      <div v-for="video in searchResults" :key="video.id" class="col-md-4">
        <iframe
          width="420"
          height="235"
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
