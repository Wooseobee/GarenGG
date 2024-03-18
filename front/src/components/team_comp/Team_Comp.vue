<template>
  <div class="container">
    <div class="row justify-content-center">
      <div class="cards col-md-12">
        <div class="cards d-flex flex-wrap justify-content-center">
          <card
            v-for="(champion, index) in champions"
            :key="index"
            :champimage="champion.image"
          />
          <!-- <youtube 
          
          /> -->
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import Card from "../common/Card.vue";
import Youtube from "../common/Youtube.vue";
const champions = [
  {
    image:
      "https://ddragon.leagueoflegends.com/cdn/img/champion/loading/Aatrox_0.jpg",
    q: "아트록스 강의",
  },
  {
    image:
      "https://ddragon.leagueoflegends.com/cdn/img/champion/loading/Garen_0.jpg",
    q: "가렌 강의",
  },
  {
    image:
      "https://ddragon.leagueoflegends.com/cdn/img/champion/loading/Gnar_0.jpg",
    q: "나르 강의",
  },
];

const searchYouTube = async () => {
  try {
    const response = await axios.get(
      "https://www.googleapis.com/youtube/v3/search",
      {
        params: {
          q: query,
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

<style scoped>
.cards {
  margin-top: 100px; /* 필요한 경우 조절하세요 */
  flex-wrap: wrap;
}

@media (min-width: 768px) {
  .cards {
    flex-wrap: nowrap;
  }
}
</style>
