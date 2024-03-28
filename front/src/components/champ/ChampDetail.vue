<template>
  <div>
    <div class="champion-info">
      <div class="champion-portrait">
        <!-- 초상화 -->
        <img
          :src="`https://ddragon.leagueoflegends.com/cdn/14.6.1/img/champion/${champname}.png`"
          alt=""
        />
      </div>
      <div>
        <div style="color: #f0e6d2">
          <h2>
            {{ champData.name }}
          </h2>
          <!-- <p>{{ champData.title }}</p> -->
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
              :description="removeHTMLTags(passiveDescription)"
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
              :tooltip="`${removeHTMLTags(activeSpellData.tooltip)}`"
              :description="activeSpellData.description"
              :cooldownBurn="`재사용대기시간: ${activeSpellData.cooldownBurn} 초`"
              :costBurn="`소모: ${getCostBurnDisplay(
                activeSpellData.costBurn
              )}`"
              :rangeBurn="`범위: ${activeSpellData.rangeBurn}`"
              v-if="activeSpell === 'spell' && modalVisible"
            />
          </div>
          <!-- 스킬 끝 -->
        </div>
      </div>
    </div>

    <div>
      <div class="box">
        <h2>설정</h2>
        <p>{{ champData.lore }}</p>
      </div>

      <div class="box">
        <h2>플레이 팁</h2>
        <ul>
          <li v-for="(tip, index) in champData.allytips" :key="index">
            {{ tip }}
          </li>
        </ul>
      </div>
    </div>
    <!-- 유튜브 -->
    <h1>챔피언 플레이 가이드</h1>
    <div>
      <Youtube :searchResults="searchResults" />
    </div>
    <!-- 스킨 -->
    <h1>스킨</h1>
    <div v-if="champData.skins">
      <Carousel
        :skins="champData.skins.slice(1).reverse()"
        :getSkinImageURL="getSkinImageURL"
      />
    </div>

    <!-- 스킨 끝 -->
  </div>
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
import Carousel from "@/components/common/Carousel.vue";
import Header from "../common/Header.vue";
const champData = ref([]);

const { champname } = defineProps({
  champname: {
    type: String,
    required: true,
  },
});

onMounted(async () => {
  getLatestPatch();
  await getChampData(champname);
});

// 영상 가져오기
const searchResults = ref([]);
const searchYouTube = async (query, publishedAfter) => {
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
          publishedAfter: publishedAfter,
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
const getCostBurnDisplay = (costBurn) => {
  return costBurn === "0" ? "소모값 없음" : costBurn;
};
const removeHTMLTags = (str) => {
  str = str.replace(/<[^>]*>?/gm, "");
  str = str.replace(/{{(.*?)}}/g, "?");
  return str;
};

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

    const MonthAgo = new Date();
    MonthAgo.setMonth(MonthAgo.getMonth() - 3);
    await searchYouTube(
      `롤 ${champData.value.name} 강의`,
      MonthAgo.toISOString()
    );
  } catch (err) {
    console.log(err);
  }
};
const route = useRoute();
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

/* box */
.box {
  display: inline-block; /* 인라인 요소로 표시하여 가로로 배열되도록 설정 */
  width: 36%; /* 상자의 너비를 줄임 */
  background-color: #010a13;
  border: 1px solid #c89b3c;
  border-radius: 5px;
  padding: 20px;
  margin-right: 20px; /* 오른쪽 여백 추가 */
  margin-bottom: 20px;
  vertical-align: top; /* 상자를 상단으로 정렬 */
  box-sizing: border-box; /* 패딩과 테두리를 상자 크기에 포함 */
}

.box h2 {
  color: #c89b3c;
  margin-top: 0;
}

.box p {
  margin: 0;
}

.box ul {
  list-style-type: none;
  padding: 0;
}

.box ul li {
  margin-bottom: 5px;
}
</style>
