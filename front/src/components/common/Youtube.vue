<template>
  <div>
    <input
      type="text"
      v-model="query"
      @keyup.enter="searchYouTube"
      placeholder="검색어를 입력하세요"
    />
    <div v-if="searchResults.length">
      <div v-for="video in searchResults" :key="video.id">
        <iframe
          width="560"
          height="315"
          :src="'https://www.youtube.com/embed/' + video.id"
          frameborder="0"
          allowfullscreen
        ></iframe>
        <p>{{ video.title }}</p>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from "vue";
import axios from "axios";

const query = ref("");
const searchResults = ref([]);

const searchYouTube = async () => {
  try {
    const response = await axios.get(
      "https://www.googleapis.com/youtube/v3/search",
      {
        params: {
          q: query.value,
          part: "snippet",
          type: "video",
          key: "AIzaSyC7dCyrkYg_AJKe-MuFmA9D0KzMZcoS6eM", // Your YouTube Data API key here
        },
      }
    );
    searchResults.value = response.data.items.map((item) => ({
      id: item.id.videoId,
      title: item.snippet.title,
    }));
  } catch (error) {
    console.error("Error fetching YouTube search results:", error);
  }
};
</script>

<style scoped></style>
