<template>
  <h2>{{ champData.name }}</h2>
  <h4>{{ champData.title }}</h4>
  <div class="champion-info">
    <div class="champion-portrait">
      <!-- 초상화 -->
      <img
        :src="`https://ddragon.leagueoflegends.com/cdn/14.6.1/img/champion/${champname}.png`"
        alt=""
      />
    </div>
    <!-- 패시브 -->
    <div class="skills-container">
      <div class="skill">
        <img
          v-if="champData.passive && champData.passive.image"
          :src="`https://ddragon.leagueoflegends.com/cdn/14.6.1/img/passive/${champData.passive.image.full}`"
          alt=""
          @mouseover="showModal('passive')"
          @mouseleave="closeModal"
        />
        <SkillModal
          :name="passiveName"
          :description="passiveDescription"
          v-if="activeSpell === 'passive' && modalVisible"
        />
      </div>
      <!-- 패시브 끝 -->
      <!-- 스킬 -->
      <div v-if="champData.spells">
        <img
          v-for="(spell, index) in champData.spells"
          :key="index"
          :src="getSpellImageURL(spell.id)"
          alt=""
          @mouseover="showModal('spell', spell)"
          @mouseleave="closeModal"
          :style="{
            marginRight: index < champData.spells.length - 1 ? '20px' : '0',
          }"
        />

        <SkillModal
          :name="activeSpellData.name"
          :tooltip="activeSpellData.tooltip"
          :description="activeSpellData.description"
          v-if="activeSpell === 'spell' && modalVisible"
        />
      </div>
      <!-- 스킬 끝 -->
    </div>
  </div>

  <div v-if="champData">
    {{ champData.lore }}
  </div>

  <div>
    <ul>
      <li v-for="(tip, index) in champData.allytips" :key="index">{{ tip }}</li>
    </ul>
  </div>
  <div>
    <Youtube :searchResults="searchResults" />
  </div>
  <!-- 스킨 -->
  <div v-if="champData.skins">
    <img
      v-for="(skin, index) in champData.skins"
      :key="index"
      :src="getSkinImageURL(skin.num)"
      alt=""
      :style="{
        marginRight: index < champData.spells.length - 1 ? '20px' : '0',
      }"
    />
  </div>
  <div>{{ champData }}</div>
  <div>
    {{ latestPatch }}
  </div>
</template>

<script setup>
import { ref, onMounted } from "vue";
import { useRoute } from "vue-router";
import Youtube from "../common/Youtube.vue";
import SkillModal from "@/components/common/SkillModal.vue";
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
          maxResults: 6,
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
// 스킨 정보
const getSkinImageURL = (skinnum) => {
  return `https://ddragon.leagueoflegends.com/cdn/img/champion/loading/${champname}_${skinnum}.jpg`;
};
// 스킬 정보
const getSpellImageURL = (spellId) => {
  return `https://ddragon.leagueoflegends.com/cdn/14.6.1/img/spell/${spellId}.png`;
};
const modalVisible = ref(false);
const activeSpell = ref(null);
const activeSpellData = ref(null);
const passiveName = ref("");
const passiveDescription = ref("");
const showModal = (type, spell = null) => {
  modalVisible.value = true;
  activeSpell.value = type;
  if (type === "passive") {
    passiveName.value = champData.passive.name;
    passiveDescription.value = champData.passive.description;
  } else if (type === "spell") {
    activeSpellData.value = spell;
  }
};

const closeModal = () => {
  modalVisible.value = false;
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
    passiveName.value = champData.value.passive.name;
    passiveDescription.value = champData.value.passive.description;
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

<style scoped>
.champion-info {
  display: flex;
}
.champion-portrait {
  margin-right: 20px; /* 초상화와 스킬 컨테이너 사이 간격 조정 */
  flex: none; /* 크기 고정 */
}
.skills-container {
  display: flex;
  align-items: center;
}
.skill {
  margin-right: 20px; /* 패시브와 스킬 사이 간격 조정 */
  flex: none; /* 크기 고정 */
}
</style>
