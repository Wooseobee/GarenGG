<template>
  <h2>{{ champname }}</h2>
  <div>
    <img
      :src="`https://ddragon.leagueoflegends.com/cdn/14.6.1/img/champion/${champname}.png`"
      alt=""
    />
    <div v-if="champData">
      {{ champData.name }}
      {{ champData.title }}
      {{ champData.lore }}
    </div>
  </div>
  <img
    v-if="champData.passive && champData.passive.image"
    :src="`https://ddragon.leagueoflegends.com/cdn/14.6.1/img/passive/${champData.passive.image.full}`"
    alt=""
    @mouseover="showPassiveInfo(true)"
    @mouseleave="showPassiveInfo(false)"
  />
  <div v-if="passiveModal">
    <h2>{{ passiveInfo.name }}</h2>
    <h3>{{ passiveInfo.description }}</h3>
  </div>
  <div v-if="champData.spells">
    <img
      v-for="(spell, index) in champData.spells"
      :key="index"
      :src="getSpellImageURL(spell.id)"
      alt=""
    />
  </div>
  <div>
    <ul>
      <li v-for="(tip, index) in champData.allytips" :key="index">{{ tip }}</li>
    </ul>
  </div>
  <Youtube :searchResults="searchResults" />
  <div>{{ champData }}</div>
  <div>
    {{ latestPatch }}
  </div>
</template>

<script setup>
import { ref, onMounted } from "vue";
import { useRoute } from "vue-router";
import Youtube from "../common/Youtube.vue";
import axios from "axios";
const champData = ref([]);
const { champname } = defineProps({
  champname: {
    type: String,
    required: true,
  },
});

// 영상 가져오기
const searchResults = ref([]);
const searchYouTube = async (query) => {
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

// 최신 패치 정보 가져오기
const latestPatch = ref("");
const getLatestPatch = function () {
  axios({
    method: "get",
    url: "https://ddragon.leagueoflegends.com/api/versions.json",
  })
    .then((res) => {
      latestPatch.value = res.data[0];
    })
    .catch((err) => {
      console.log(err);
    });
};
// 스킬 정보
const getSpellImageURL = (spellId) => {
  return `https://ddragon.leagueoflegends.com/cdn/14.6.1/img/spell/${spellId}.png`;
};
const passiveInfo = ref({ name: "", description: "" });
const passiveModal = ref(false);
const showPassiveInfo = (isVisible) => {
  passiveModal.value = isVisible;
  if (champData.passive) {
    passiveInfo.value = {
      name: champData.passive.name,
      description: champData.passive.description,
    };
  }
};

// 챔피언 정보 가져오기
const getChampData = async (champname) => {
  try {
    const res = await axios({
      method: "get",
      url: `https://ddragon.leagueoflegends.com/cdn/14.6.1/data/ko_KR/champion/${champname}.json`,
    });
    console.log(res);
    champData.value = res.data.data[champname];
    await searchYouTube(`롤 ${champData.value.name} 강의`);
  } catch (err) {
    console.log(err);
  }
};
const route = useRoute();
onMounted(async () => {
  getLatestPatch();
  await getChampData(champname);
});
</script>

<style scoped></style>
